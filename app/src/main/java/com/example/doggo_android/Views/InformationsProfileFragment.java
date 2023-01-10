package com.example.doggo_android.Views;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.ViewModels.ConnectionViewModel;
import com.example.doggo_android.databinding.FragmentInformationsProfileBinding;
import com.example.doggo_android.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationsProfileFragment extends Fragment {
    FragmentInformationsProfileBinding binding;
    RetrofitRequests requests;
    String token;
    ConnectionViewModel connectionViewModel;
    IUser user;

    public InformationsProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInformationsProfileBinding.inflate(inflater, container, false);
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
                        binding.textViewName.setText("Nom : " + user.getName());
                        binding.textViewSurname.setText("Prenom : " +user.getSurname());
                        binding.textViewEmail.setText("Adresse e-mail : " +user.getEmail());
                        binding.textViewPhone.setText("Numéro de téléphone : " +user.getPhone());
                        binding.textViewRole.setText("Rôle : " +user.getRole());
                    }
                } else {
                    Log.d(TAG, "onResponse: Token is invalid");
                    NavHostFragment.findNavController(InformationsProfileFragment.this).navigate(R.id.action_profileFragment_to_connectionFragment);
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
                    binding.textViewName.setText(user.getName());
                    binding.textViewSurname.setText(user.getSurname());
                    binding.textViewEmail.setText(user.getEmail());
                    binding.textViewPhone.setText(user.getPhone());
                    binding.textViewRole.setText(user.getRole());

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