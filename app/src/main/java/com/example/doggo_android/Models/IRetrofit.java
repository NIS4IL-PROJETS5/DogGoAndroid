package com.example.doggo_android.Models;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRetrofit {
    @POST("/api/auth/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("api/auth/signup")
    Call<Void> executeRegister(@Body HashMap<String, String> map);
}
