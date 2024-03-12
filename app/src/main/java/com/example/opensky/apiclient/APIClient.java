package com.example.opensky.apiclient;

public class APIClient {

    private static final String BASE_URL = "https://api.chucknorris.io";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
