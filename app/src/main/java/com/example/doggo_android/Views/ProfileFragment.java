package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String apiUrl = Utils.getConfigValue(requireContext(), "api_url");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create()).build();

        requests = retrofit.create(RetrofitRequests.class);

        binding.profileLoginButton.setOnClickListener(v -> handleLoginDialog());
        binding.profileSignupButton.setOnClickListener(v -> handleSignupDialog());
    }

    private void handleLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.dialog_login_button);
        EditText emailEdit = view.findViewById(R.id.dialog_login_email_input);
        EditText passwordEdit = view.findViewById(R.id.dialog_login_password_input);

        loginBtn.setOnClickListener(v -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("email", emailEdit.getText().toString());
            map.put("password", passwordEdit.getText().toString());

            Call<IUser> call = requests.executeLogin(map);

            call.enqueue(new Callback<IUser>() {
                @Override
                public void onResponse(Call<IUser> call, Response<IUser> response) {
                    switch (response.code()) {
                        case 200:
                            IUser result = response.body();
                            Utils.alertDialogHandler(getContext(), "Login Success", "UserId: " + result.getId() + "\n" + "Role: " + result.getRole() + "\n" + "Token: " + result.getToken());
                            break;

                        case 401:
                            Utils.alertDialogHandler(getContext(), "Login Failed", "Wrong email or password");
                            break;

                        case 500:
                            Utils.alertDialogHandler(getContext(), "Login Failed", "Server Error");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<IUser> call, Throwable t) {
                    Utils.alertDialogHandler(getContext(), "Error", t.getMessage());
                }
            });
        });
    }

    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.dialog_signup, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view).show();

        Button registerBtn = view.findViewById(R.id.dialog_signup_button);
        EditText emailEdit = view.findViewById(R.id.dialog_signup_email_input);
        EditText passwordEdit = view.findViewById(R.id.dialog_signup_password_input);
        EditText nameEdit = view.findViewById(R.id.dialog_signup_name_input);

        registerBtn.setOnClickListener(v -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("email", emailEdit.getText().toString());
            map.put("password", passwordEdit.getText().toString());
            map.put("name", nameEdit.getText().toString());

            Call<Void> call = requests.executeRegister(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    switch (response.code()) {
                        case 201:
                            Utils.alertDialogHandler(getContext(), "Success", "User created successfully");
                            break;

                        case 400:
                            Utils.alertDialogHandler(getContext(), "Error", "Invalid email or password");
                            break;

                        case 500:
                            Utils.alertDialogHandler(getContext(), "Error", "Internal server error");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Utils.alertDialogHandler(getContext(), "Error", t.getMessage());
                }
            });
        });
    }
}

