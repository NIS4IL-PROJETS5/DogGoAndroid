package com.example.doggo_android;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.doggo_android.databinding.ActivityCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {

    ActivityCameraBinding binding;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    ImageCapture imageCapture;
    Camera camera;
    Preview preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCamera(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
                Log.d("PHOTODOC", "onCreate: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));

        binding.floatingActionButton.setOnClickListener(v -> onCameraButtonClick());

    }

    void bindCamera(@NonNull ProcessCameraProvider cameraProvider) {
        preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.camera.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setTargetRotation(binding.getRoot().getDisplay().getRotation())
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetResolution(new android.util.Size(1280, 720))
                .build();

        camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture, preview);
    }

    @OptIn(markerClass = androidx.camera.core.ExperimentalGetImage.class)
    public void onCameraButtonClick(){
        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {

            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                super.onCaptureSuccess(image);
                Log.d("PHOTODOC", "onCaptureSuccess: Image recup");

                binding.buttonAcceptPhoto.setVisibility(View.VISIBLE);
                binding.buttonRefusePhoto.setVisibility(View.VISIBLE);

                binding.buttonAcceptPhoto.setOnClickListener(v -> onAcceptPhotoClick(image));

                preview.setSurfaceProvider(null);

            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                super.onError(exception);
                Log.d("PHOTODOC", "onError: " + exception.getMessage());
            }
        });
    }


    public void onAcceptPhotoClick(ImageProxy image){
        File file = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DCIM, System.currentTimeMillis() + ".jpg");


    }
}