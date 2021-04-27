package com.muvlin.app.apiclient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit ourInstance;
    private static String API_URL = "https://api.muvlin.com/api/";

    public static Retrofit getInstance() {
        System.out.println("URL: " + API_URL);
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return ourInstance;
    }

    private ApiClient() {
    }
}
