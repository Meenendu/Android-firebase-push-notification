package com.example.fcmpushnotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCalls {
    @POST("/send/{token}")
    Call<Void> sendMessage(@Body MessageData message, @Path("token") String token);
}
