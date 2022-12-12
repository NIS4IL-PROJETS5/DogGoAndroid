package com.example.doggo_android.Models;

import java.util.ArrayList;
import java.util.Date;
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

    @GET("/api/auth/user/{id}")
    Call<IUser> executeGetUser(@Path("id") String userId, @Header("Authorization") String token);

    @GET("/api/actualites/limit/{limit}")
    Call<ArrayList<IActus>> executeGetActus(@Header("Authorization") String token, @Path("limit") String limit);

    @GET("api/auth/check")
    Call<IUser> executeCheckToken(@Header("Authorization") String token);
    
    @POST("api/contacts/create")
    Call<IContact> executeContact(@Body HashMap<String, String> map, @Header("Authorization") String token);
    @POST("api/dogs/create")
    Call<IDog> executeCreateDog(@Body HashMap<String, String> map,@Header("Authorization") String token);
}
