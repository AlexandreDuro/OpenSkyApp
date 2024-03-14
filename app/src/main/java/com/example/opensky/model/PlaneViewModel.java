package com.example.opensky.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.opensky.OpenSkyStates;
import com.example.opensky.apiclient.APIClient;
import com.example.opensky.apiclient.apiinterface.OpenSkyService;
import com.example.opensky.apiclient.apiinterface.OpenWeatherMapService;
import com.example.opensky.model.Location;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaneViewModel extends ViewModel {

    private MutableLiveData<List<StateVector>> planes = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private OpenSkyService openSkyService;
    private OpenWeatherMapService openWeatherMapService;

    public PlaneViewModel() {
        openSkyService = APIClient.getOpenSkyClient().create(OpenSkyService.class);
        openWeatherMapService = APIClient.getOpenWeatherMapClient().create(OpenWeatherMapService.class);
    }

    public MutableLiveData<List<StateVector>> getPlanes() {
        return planes;
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

        openSkyService.getAllStates(latMin, lonMin, latMax, lonMax).enqueue(new Callback<OpenSkyStates>() {
            @Override
            public void onResponse(Call<OpenSkyStates> call, Response<OpenSkyStates> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OpenSkyStates states = response.body();
                    if (states.getStates() != null) {
                        List<StateVector> stateVectors = new ArrayList<>();
                        for (List<Object> state : states.getStates()) {
                            String icao24 = (String) state.get(0);
                            String callsign = state.size() > 1 ? (String) state.get(1) : "";
                            stateVectors.add(new StateVector(icao24, callsign.trim()));
                            Log.d("PlaneViewModel", "onResponse: " + icao24 + " " + callsign);
                        }
                        planes.postValue(stateVectors);
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


}
