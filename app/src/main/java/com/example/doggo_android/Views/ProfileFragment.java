package com.example.doggo_android.Views;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentProfileBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    RetrofitRequests requests;
    private static final String TAG = "ProfileFragment";

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.checkToken();

        binding.buttonProfileModify2.setOnClickListener(v -> {
            //naviagte to candidature form
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            assert navHostFragment != null;
            NavController controller = navHostFragment.getNavController();
            controller.navigate(R.id.action_profileFragment_to_candidatureFormFragment);
        });
    }

    public void checkToken() {
        String apiUrl = Utils.getConfigValue(requireContext(), "api_url");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            requests = retrofit.create(RetrofitRequests.class);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e);
        }

        SharedPreferences preferences = requireActivity().getSharedPreferences("DogGo", 0);
        String token = preferences.getString("token", "");
        Call<Void> call = requests.executeCheckToken("Bearer " + token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: Token is valid");
                } else {
                    Log.d(TAG, "onResponse: Token is invalid");
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_profileFragment_to_connectionFragment);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


}

