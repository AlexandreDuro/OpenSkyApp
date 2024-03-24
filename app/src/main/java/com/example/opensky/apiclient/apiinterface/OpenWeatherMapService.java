package com.example.opensky.apiclient.apiinterface;

import com.example.opensky.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {
    @GET("geo/1.0/direct")
    Call<List<Location>> getCoordinates(
            @Query("q") String query,
            @Query("limit") int limit,
            @Query("appid") String appid
    );
}