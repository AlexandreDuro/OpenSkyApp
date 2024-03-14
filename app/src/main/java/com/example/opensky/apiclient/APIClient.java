package com.example.opensky.apiclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String OPENSKY_BASE_URL = "https://opensky-network.org/api/";
    private static final String OPENWEATHERMAP_BASE_URL = "https://api.openweathermap.org/";

    public static Retrofit getOpenSkyClient() {
        return new Retrofit.Builder()
                .baseUrl(OPENSKY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getOpenWeatherMapClient() {
        return new Retrofit.Builder()
                .baseUrl(OPENWEATHERMAP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
