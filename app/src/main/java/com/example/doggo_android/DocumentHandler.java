package com.example.doggo_android;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.doggo_android.Models.FileHandlerService;
import com.example.doggo_android.Models.IUserDocument;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentHandler {

    public static void uploadFileCreate(String token, String docID, File file, Context ctx) throws IOException {
        FileHandlerService fileHandlerService = RetrofitClientInstance.getRetrofitInstance(ctx).create(FileHandlerService.class);

        String mimeType;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mimeType = Files.probeContentType(file.toPath());
        } else {
            mimeType = URLConnection.guessContentTypeFromName(file.getName());
        }

        RequestBody reqFile = RequestBody.create(MediaType.parse(mimeType), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        RequestBody reqDocID = RequestBody.create(MediaType.parse("text/plain"), docID);

        Call<IUserDocument> call = fileHandlerService.uploadFileCreate(token, body, reqDocID);

        Log.d("DOC", "uploadFileCreate: " + call.request());

        call.enqueue(new Callback<IUserDocument>() {
            @Override
            public void onResponse(Call<IUserDocument> call, Response<IUserDocument> response) {
                if (response.code() == 201){
                    Toast.makeText(ctx, "Fichier mis en ligne", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Erreur lors de l'envoi du fichier", Toast.LENGTH_SHORT).show();
                    Log.d("DOC", "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<IUserDocument> call, Throwable t) {
                Toast.makeText(ctx, "Erreur lors de l'envoi du fichier", Toast.LENGTH_SHORT).show();
                Log.d("DOC", "onFailure: " + t.getMessage());
            }
        });
    }

    public static void uploadFile(String token, String docID, File file, Context ctx) throws  IOException{
        FileHandlerService fileHandlerService = RetrofitClientInstance.getRetrofitInstance(ctx).create(FileHandlerService.class);

        String mimeType;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mimeType = Files.probeContentType(file.toPath());
        } else {
            mimeType = URLConnection.guessContentTypeFromName(file.getName());
        }

        RequestBody reqFile = RequestBody.create(MediaType.parse(mimeType), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        Call<IUserDocument> call = fileHandlerService.uploadFileModify(token,docID,body);

        call.enqueue(new Callback<IUserDocument>() {
            @Override
            public void onResponse(Call<IUserDocument> call, Response<IUserDocument> response) {
                if (response.code() == 200){
                    Toast.makeText(ctx, "Fichier mis en ligne", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Erreur lors de l'envoi du fichier", Toast.LENGTH_SHORT).show();
                    Log.d("DOC", "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<IUserDocument> call, Throwable t) {
                Toast.makeText(ctx, "Erreur lors de l'envoi du fichier", Toast.LENGTH_SHORT).show();
                Log.d("DOC", "onFailure: " + t.getMessage());
            }
        });
    }

}
