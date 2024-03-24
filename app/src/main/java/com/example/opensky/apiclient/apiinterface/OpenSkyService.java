package com.example.opensky.apiclient.apiinterface;

import com.example.opensky.OpenSkyStates;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenSkyService {
    @GET("states/all")
    Call<OpenSkyStates> getAllStates(
            @Query("lamin") double lamin,
            @Query("lomin") double lomin,
            @Query("lamax") double lamax,
            @Query("lomax") double lomax
    );

}
