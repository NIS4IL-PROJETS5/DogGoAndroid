package com.example.doggo_android.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitRequests {
    @POST("/api/auth/login")
    Call<IUser> executeLogin(@Body HashMap<String, String> map);

    @POST("api/auth/signup")
    Call<Void> executeRegister(@Body HashMap<String, String> map);

    @GET("/api/auth/user/{id}")
    Call<IUser> executeGetUser(@Path("id") String userId, @Header("Authorization") String token);

    @GET("/api/actualites/limit/{type}/{limit}")
    Call<ArrayList<IActus>> executeGetActus(@Header("Authorization") String token, @Path("type") String type, @Path("limit") String limit);

    @GET("/api/auth/users")
    Call<List<IUser>> executeGetUsers(@Header("Authorization") String token);

    @GET("/api/docs/doc/user/status/{id}")
    Call<IDOC_STATUSWrapper> executeGetDocStatus(@Path("id") String userId, @Header("Authorization") String token);
    @PUT("/api/auth/update/{id}")
    Call<IUser> executeUpdateUser(@Path("id") String userId, @Body HashMap<String, String> map, @Header("Authorization") String token);

    @GET("/api/actualites/limit/{limit}")
    Call<ArrayList<IActus>> executeGetActus(@Header("Authorization") String token, @Path("limit") String limit);

    @GET("api/auth/check")
    Call<IUser> executeCheckToken(@Header("Authorization") String token);
    
    @POST("api/contacts/create")
    Call<IContact> executeContact(@Body HashMap<String, String> map, @Header("Authorization") String token);
    @POST("api/dogs/create")
    Call<IDog> executeCreateDog(@Body HashMap<String, String> map,@Header("Authorization") String token);

    @GET("api/docs/reqDoc/user/sentornot")
    Call<ArrayList<IRequiredDocument>> executeGetUserDocsAndRequiredDocs(@Header("Authorization") String token);

    @GET("api/docs/reqDocs")
    Call<ArrayList<IRequiredDocument>> executeGetAllRequiredDocs(@Header("Authorization") String token);

    @GET("api/docs/userDocs")
    Call<ArrayList<IUserDocument>> executeGetUserDocs(@Header("Authorization") String token);

    @GET("api/docs/userDocs/admin/{id}")
    Call<ArrayList<IUserDocument>> executeGetUserDocsAdmin(@Header("Authorization") String token, @Path("id") String id);

    @GET("api/docs/userDocs/admin/all")
    Call<ArrayList<IUserDocument>> executeGetAllUserDocs(@Header("Authorization") String token);

    @POST("api/docs/reqDoc/create")
    Call<IRequiredDocument> executeCreateReqDoc(@Body HashMap<String, String> map,@Header("Authorization") String token);

    @PUT("api/docs/reqDoc/update/{id}")
    Call<IRequiredDocument> executeUpdateReqDoc(@Body HashMap<String, String> map,@Header("Authorization") String token, @Path("id") String id);

    @DELETE("api/docs/reqDoc/delete/{id}")
    Call<Void> executeDeleteReqDoc(@Header("Authorization") String token, @Path("id") String id);

    @GET("api/docs/doc/{id}")
    Call<IUserDocument> executeGetDoc(@Path("id") String docId, @Header("Authorization") String token);

    @PUT("api/docs/doc/update/{id}")
    Call<IUserDocument> executeUpdateDoc(@Path("id") String docId, @Body HashMap<String, String> map, @Header("Authorization") String token);
}
