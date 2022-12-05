package com.example.doggo_android.Views;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.doggo_android.ViewModels.ConnectionViewModel;
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
    String token;
    ConnectionViewModel connectionViewModel;
    IUser user;
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
        connectionViewModel = new ViewModelProvider(requireActivity()).get(ConnectionViewModel.class);
        this.requests = Utils.getRetrofitCon(requireContext());
        this.token = Utils.getToken(requireContext());
        this.user = connectionViewModel.getUser();
        this.checkToken();

        binding.buttonProfileModify2.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            assert navHostFragment != null;
            NavController controller = navHostFragment.getNavController();
            controller.navigate(R.id.action_profileFragment_to_candidatureFormFragment);
        });

        binding.buttonProfileSignout.setOnClickListener(v -> {
            SharedPreferences preferences = getActivity().getSharedPreferences("DogGo", 0);
            preferences.edit().clear().apply();
            CompteFragment.checkLogin(requireContext());
        });
    }

    public void checkToken() {
        Call<IUser> call = this.requests.executeCheckToken("Bearer " + this.token);

        call.enqueue(new Callback<IUser>() {
            @Override
            public void onResponse(Call<IUser> call, Response<IUser> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: Token is valid");
                    if (user.getName() == null) {
                        user = response.body();
                        connectionViewModel.setUser(user);
                        fetchUser();
                    } else {
                        binding.textViewPrenom.setText(user.getName());
                        binding.textViewNom.setText(user.getSurname());
                    }
                } else {
                    Log.d(TAG, "onResponse: Token is invalid");
                }
            }

            @Override
            public void onFailure(Call<IUser> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    public void fetchUser() {

        Call<IUser> call = this.requests.executeGetUser(user.getId(), "Bearer " + this.token);

        call.enqueue(new Callback<IUser>() {
            @Override
            public void onResponse(Call<IUser> call, Response<IUser> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: User fetched");
                    user = response.body();
                    connectionViewModel.setUser(user);
                    binding.textViewPrenom.setText(user.getName());
                    binding.textViewNom.setText(user.getSurname());
                } else {
                    Log.d(TAG, "onResponse: User not fetched");
                }
            }

            @Override
            public void onFailure(Call<IUser> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}

