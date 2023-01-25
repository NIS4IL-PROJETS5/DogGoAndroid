package com.example.doggo_android.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doggo_android.Models.IDog;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentCandidatureFormBinding;
import com.example.doggo_android.databinding.FragmentProfileBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CandidatureFormFragment extends Fragment {
    FragmentCandidatureFormBinding binding;
    RetrofitRequests requests;
    private static final String TAG = "CandidatureFragment";

    public CandidatureFormFragment() {
        // Required empty public constructor

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String apiUrl = Utils.getConfigValue(requireContext(), "api_url");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create()).build();
            requests = retrofit.create(RetrofitRequests.class);
        } catch (Exception e) {
            Log.e(TAG, "onViewCreated: " + e.getMessage());
        }
        binding.buttonUpload.setOnClickListener(v -> {
            EditText dogName = binding.editTextDogName;
            EditText dogAge = binding.editTextDogAge;
            EditText dogBreed = binding.editTextDogRace;
            HashMap<String, String> map = new HashMap<>();
            map.put("name", dogName.getText().toString());
            map.put("age", dogAge.getText().toString());
            map.put("breed", dogBreed.getText().toString());
            SharedPreferences preferences = requireActivity().getSharedPreferences("token", 0);
            String token = preferences.getString("token", "");
            Call<IDog> call = requests.executeCreateDog(map, "Bearer " + token);            call.enqueue(new retrofit2.Callback<IDog>() {
                @Override
                public void onResponse(Call<IDog> call, retrofit2.Response<IDog> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Dog created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<IDog> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("TAG", "File Uri: " + uri.toString());
                    // Get the path
                    String path = requireContext().getFilesDir().getAbsolutePath();
                    Log.d("TAG", "File Path: " + path);
                    // Get the file instance
                    File imgFile = new File(uri.getPath());
                    // Initiate the upload
                    Uri selectedImage = data.getData();
                    ImageView imageView = binding.imageViewSelectFile;
                    imageView.setImageURI(selectedImage);

                    break;
                }
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentCandidatureFormBinding.inflate(inflater, container, false);
        Button button = binding.buttonSelectFile;
        button.setOnClickListener((View.OnClickListener) v -> {
            showFileChooser();
        });



        return binding.getRoot();
    }

}