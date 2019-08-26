package com.example.fcmpushnotification;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {

    private static ApiCalls apiCalls = null;
    private static Retrofit retrofit = null;

    public static ApiCalls getInstance() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8081")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (apiCalls == null) {
            apiCalls = retrofit.create(ApiCalls.class);
        }
        return apiCalls;

    }
}
