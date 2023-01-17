package com.example.doggo_android.Models;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FileHandlerService {
    @Multipart
    @POST("api/docs/doc/create")
    Call<IUserDocument> uploadFileCreate(@Header("Authorization") String token,
                                   @Part() MultipartBody.Part file,
                                   @Part("docId") RequestBody reqDocID);

    @Multipart
    @POST("api/docs/doc/add/{id}")
    Call<IUserDocument> uploadFileModify(@Header("Authorization") String token,
                                         @Path("id") String docId,
                                         @Part() MultipartBody.Part file);
}
