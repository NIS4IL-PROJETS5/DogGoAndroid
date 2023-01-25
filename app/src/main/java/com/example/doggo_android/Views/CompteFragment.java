package com.example.doggo_android.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentCompteBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CompteFragment extends Fragment implements Callback{

    static FragmentCompteBinding binding;
    private static CompteFragment instance;

    public CompteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompteBinding.inflate(inflater, container, false);
        instance = this;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkLogin(requireContext());
    }

    /**
     * It checks if the user is logged in or not.
     *
     * @param context The context of the activity.
     */
    public static void checkLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences("DogGo", 0);// 0 - for private mode
        String checkLogin = pref.getString("token", null);

        String url = Utils.getConfigValue(context, "api_url") + "api/auth/check";

        Log.d("URL", "checkLogin: " + url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + checkLogin)
                .build();

        if (checkLogin != null) {
            client.newCall(request).enqueue(instance);
        } else{
            instance.hideProfile();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        hideProfile();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
            Log.d("Connexion", "onResponse: " + response.body().string());
            if (response.code() == 200){
                hideConnexion();
            } else {
                hideProfile();
            }
    }


    private void hideConnexion(){
        requireActivity().runOnUiThread(() -> {
            binding.fragmentContainerViewConnexion.setVisibility(View.INVISIBLE);
            binding.fragmentContainerViewProfile.setVisibility(View.VISIBLE);
        });
    }

    private void hideProfile(){
        requireActivity().runOnUiThread(() -> {
            binding.fragmentContainerViewConnexion.setVisibility(View.VISIBLE);
            binding.fragmentContainerViewProfile.setVisibility(View.INVISIBLE);
        });
    }
}

