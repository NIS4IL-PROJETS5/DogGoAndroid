package com.example.doggo_android.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class CameraUtilClass {

    static File latestFile;

    public static Uri createImageUri(Activity activity) {
        File file = new File(activity.getExternalCacheDir(), String.valueOf(System.currentTimeMillis()) + ".jpg");
        latestFile = file;
        return Uri.fromFile(file);
    }

    public static File getLatestFile() {
        return latestFile;
    }
}
