package com.example.doggo_android.Views;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.ViewModels.ConnectionViewModel;
import com.example.doggo_android.databinding.FragmentConnectionBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionFragment extends Fragment {

    FragmentConnectionBinding binding;
    RetrofitRequests requests;
    ConnectionViewModel connectionViewModel;
    private static final String TAG = "ConnectionFragment";
    public ConnectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentConnectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
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

        binding.profileLoginButton.setOnClickListener(v -> handleLoginDialog());
        binding.profileSignupButton.setOnClickListener(v -> handleSignupDialog());
    }

    private void handleLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);

        DialogInterface loginDialog = new AlertDialog.Builder(requireContext()).setView(view).show();

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
                public void onResponse(@NonNull Call<IUser> call, @NonNull Response<IUser> response) {
                    switch (response.code()) {
                        case 200:
                            IUser result = response.body();
                            connectionViewModel = new ViewModelProvider(requireActivity()).get(ConnectionViewModel.class);
                            connectionViewModel.setUser(result);

                            //store the token in SharedPreferences
                            SharedPreferences pref = requireContext().getSharedPreferences("DogGo", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("token", result.getToken()); // Storing string
                            editor.commit(); // commit changes

                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setTitle("Login Success").setMessage("UserId: " + result.getId() + "\n" + "Role: " + result.getRole() + "\n" + "Token: " + result.getToken()).setPositiveButton("OK", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                                loginDialog.cancel();
                                CompteFragment.checkLogin(requireContext());
                            }).show();

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
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
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
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Utils.alertDialogHandler(getContext(), "Error", t.getMessage());
                }
            });
        });
    }
}