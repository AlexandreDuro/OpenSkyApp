package com.example.opensky.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.opensky.OpenSkyStates;
import com.example.opensky.apiclient.APIClient;
import com.example.opensky.apiclient.apiinterface.OpenSkyService;
import com.example.opensky.apiclient.apiinterface.OpenWeatherMapService;
import com.example.opensky.database.AppDatabase;
import com.example.opensky.database.Plane;
import com.example.opensky.database.PlaneDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaneViewModel extends ViewModel {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final OpenSkyService openSkyService;
    private final OpenWeatherMapService openWeatherMapService;
    private final MediatorLiveData<List<Plane>> savedPlanes = new MediatorLiveData<>();
    private final MutableLiveData<List<Plane>> planesLiveData = new MutableLiveData<>();
    private final PlaneDao planeDao;

    public PlaneViewModel(Application application) {
        openSkyService = APIClient.getOpenSkyClient().create(OpenSkyService.class);
        openWeatherMapService = APIClient.getOpenWeatherMapClient().create(OpenWeatherMapService.class);
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        planeDao = appDatabase.planeDao();
        savedPlanes.addSource(planeDao.getAllPlanes(), savedPlanes::setValue);
        savedPlanes.addSource(planesLiveData, savedPlanes::setValue);
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadAirportCoordinates(String airportName) {
        Log.d("PlaneViewModel", "loadAirportCoordinates is called with: " + airportName);
        openWeatherMapService.getCoordinates(airportName, 1, "184384caa377cfe195e321fd09b02ac8").enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                Log.d("PlaneViewModel", "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Log.d("PlaneViewModel", "onResponse: " + response.body().get(0));
                    Location location = response.body().get(0);
                    double lat = location.getLat();
                    double lon = location.getLon();
                    loadPlanes(lat, lon);
                    Log.d("PlaneViewModel", "onResponse: " + lat + " " + lon);
                } else {
                    errorMessage.postValue("Error loading airport coordinates");
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                errorMessage.postValue("Failed to load airport coordinates: " + t.getMessage());
            }
        });
    }

    public void loadPlanes(double lat, double lon) {
        double radius = 1;
        double latMin = lat - radius;
        double lonMin = lon - radius;
        double latMax = lat + radius;
        double lonMax = lon + radius;

        Log.d("PlaneViewModel", "loadPlanes: Loading planes...");

        openSkyService.getAllStates(latMin, lonMin, latMax, lonMax).enqueue(new Callback<OpenSkyStates>() {
            @Override
            public void onResponse(Call<OpenSkyStates> call, Response<OpenSkyStates> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OpenSkyStates states = response.body();
                    if (states.getStates() != null) {
                        List<Plane> planes = new ArrayList<>();
                        for (List<Object> state : states.getStates()) {
                            String icao24 = (String) state.get(0);
                            String callsign = state.size() > 1 && state.get(1) != null ? (String) state.get(1) : "N/A";
                            String originCountry = state.size() > 2 && state.get(2) != null ? (String) state.get(2) : "N/A";
                            float velocity = state.size() > 9 && state.get(9) != null ? ((Number) state.get(9)).floatValue() : 0;
                            float altitude = state.size() > 13 && state.get(13) != null ? ((Number) state.get(13)).floatValue() : 0;
                            planes.add(new Plane(icao24, callsign.trim(), originCountry, velocity, altitude));
                            Log.d("PlaneViewModel", "loadPlanes: Plane added - ICAO24: " + icao24 + ", Callsign: " + callsign);
                        }
                        planesLiveData.postValue(planes);
                        Log.d("PlaneViewModel", "loadPlanes: " + planes.size() + " planes loaded");
                    } else {
                        errorMessage.postValue("No states available");
                    }
                } else {
                    errorMessage.postValue("Failed to load data: HTTP error code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OpenSkyStates> call, Throwable t) {
                errorMessage.postValue("Failed to load data: " + t.getMessage());
            }
        });
    }


    public LiveData<List<Plane>> getSavedPlanes() {
        return savedPlanes;
    }

    public LiveData<List<Plane>> getPlanes() {
        return planeDao.getAllPlanes();
    }

    public LiveData<List<Plane>> getPlanesLiveData() {
        return planesLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }

    public void resetPlanes() {
        savedPlanes.setValue(new ArrayList<>());
    }

    public void savePlane(Plane plane) {
        executorService.execute(() -> planeDao.insert(plane));
    }

    public void deletePlane(Plane plane) {
        executorService.execute(() -> planeDao.delete(plane));
    }
}
