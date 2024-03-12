package com.example.opensky.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.opensky.OpenSkyStates;
import com.example.opensky.apiclient.APIClient;
import com.example.opensky.apiclient.apiinterface.OpenSkyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaneViewModel extends ViewModel {

    private MutableLiveData<List<StateVector>> planes = new MutableLiveData<>();
    private OpenSkyService openSkyService;

    public PlaneViewModel() {
        openSkyService = APIClient.getClient().create(OpenSkyService.class);
    }

    public MutableLiveData<List<StateVector>> getPlanes() {
        return planes;
    }

    public void loadPlanes() {
        double latMin = 45.8389;
        double lonMin = 5.9962;
        double latMax = 47.8229;
        double lonMax = 10.5226;

        openSkyService.getAllStates(latMin, lonMin, latMax, lonMax).enqueue(new Callback<OpenSkyStates>() {
            @Override
            public void onResponse(Call<OpenSkyStates> call, Response<OpenSkyStates> response) {
                if (response.isSuccessful() && response.body() != null) {
                    planes.postValue(response.body().getStates());
                } else {

                }
            }

            @Override
            public void onFailure(Call<OpenSkyStates> call, Throwable t) {
            }
        });
    }
}
