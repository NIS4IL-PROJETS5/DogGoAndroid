package com.example.doggo_android.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitRequests {
    @POST("/api/auth/login")
    Call<IUser> executeLogin(@Body HashMap<String, String> map);

    @POST("api/auth/signup")
    Call<Void> executeRegister(@Body HashMap<String, String> map);

    @GET("/api/actualites/limit/{limit}")
    Call<ArrayList<IActus>> executeGetActus(@Header("Authorization") String token, @Path("limit") String limit);

    @GET("api/auth/check")
    Call<Void> executeCheckToken(@Header("Authorization") String token);
}
