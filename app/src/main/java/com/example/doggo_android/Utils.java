package com.example.doggo_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.doggo_android.Models.RetrofitRequests;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    private static final String TAG = "Utils";

    public static String getConfigValue(Context context, String name) {
        Resources resources = context.getResources();
        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }
        return null;
    }

    public static void alertDialogHandler(Context ctx, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title).setMessage(message).setPositiveButton("OK", null).show();
    }

    public static String truncate(String text, int length) {
        if (text == null || text.length() <= length) {
            return text;
        }
        return text.substring(0, length - 3) + "...";
    }

    public static RetrofitRequests getRetrofitCon(Context ctx) {
        String apiUrl = Utils.getConfigValue(ctx, "api_url");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitRequests.class);
    }

    public static String getToken(Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences("DogGo", 0);
        return preferences.getString("token", "");
    }

    public static String getRole(Context ctx) {
        SharedPreferences preferences = ctx.getSharedPreferences("DogGo", 0);
        return preferences.getString("role", "");
    }
}
