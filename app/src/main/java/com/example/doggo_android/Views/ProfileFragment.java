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

import com.example.doggo_android.MainActivity;
import com.example.doggo_android.Models.IRetrofit;
import com.example.doggo_android.Models.LoginResult;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentProfileBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private Retrofit retrofit;
    private IRetrofit retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000/";

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

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Log.d("RETRO", "onViewCreated: " + retrofit);

        retrofitInterface = retrofit.create(IRetrofit.class);

        Log.d("RETRO", "onViewCreated" + retrofitInterface);

        binding.profileLoginButton.setOnClickListener(v -> handleLoginDialog());

    }


    private void handleLoginDialog() {
        Log.d("RETRO", "handleLoginDialog: " + "Login Dialog");
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.dialog_login_button);
        EditText emailEdit = view.findViewById(R.id.dialog_login_email_input);
        EditText passwordEdit = view.findViewById(R.id.dialog_login_password_input);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        Log.d("BODY", "onResponse: " + response);
                        if (response.code() == 200) {
                            Log.d("RETRO", "200");
                            LoginResult result = response.body();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("Success").setMessage("UserId: " + result.getId() + "\n" + "Role: " + result.getRole() + "\n" + "Token: " + result.getToken()).setPositiveButton("OK", null).show();
                        } else if (response.code() == 400) {
                            Log.d("RETRO", "400");
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("Error").setMessage("Invalid email or password").setPositiveButton("OK", null).show();
                        } else if (response.code() == 401) {
                            Log.d("RETRO", "401");
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                            builder.setTitle("Error").setMessage("User not found").setPositiveButton("OK", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Log.d("RETRO", "onFailure: " + t.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Error").setMessage(t.getMessage()).setPositiveButton("OK", null).show();
                    }
                });
            }
        });

    }

}

