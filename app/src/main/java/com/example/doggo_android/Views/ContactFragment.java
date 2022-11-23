package com.example.doggo_android.Views;

import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.Models.IContact;
import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentContactBinding;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContactFragment extends Fragment{

    FragmentContactBinding binding;
    RetrofitRequests requests;


    private String message;
    private String reason;

    private int intReason;



    public ContactFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences getToken = requireActivity().getSharedPreferences("DogGo", 0);

        String apiUrl = Utils.getConfigValue(requireContext(), "api_url");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create()).build();
            requests = retrofit.create(RetrofitRequests.class);
        } catch (Exception e) {
            Log.e("ContactFragment", "onViewCreated: " + e.getMessage());
        }


        binding.sendFormContact.setOnClickListener(v -> {

            intReason = binding.spinnerChoiceReason.getSelectedItemPosition();

            message = binding.ptMessage.getText().toString();
            reason = binding.spinnerChoiceReason.getSelectedItem().toString();
            if (intReason != 0 && !message.isEmpty()) {
                HashMap<String, String> map = new HashMap<>();

                Log.d("TEST", "Raison: " + intReason);
                map.put("conDestinataire", String.valueOf(intReason));

                Log.d("TEST", "Message: " + message);
                map.put("conDemande", message);


                Call<IContact> call = requests.executeContact(map, "Bearer " + getToken.getString("token", ""));


                call.enqueue(new Callback<IContact>() {
                    @Override
                    public void onResponse(@NonNull Call<IContact> call, @NonNull Response<IContact> response) {
                        switch (response.code()) {
                            case 200:
                                Utils.alertDialogHandler(getContext(), "Contact sent", "Your message has been sent to the DogGo team. We will answer you as soon as possible.");
                                break;
                            case 400:
                                Utils.alertDialogHandler(getContext(), "Error", "An error has occurred. Please try again later.");
                                break;
                            case 401:
                                Utils.alertDialogHandler(getContext(), "Error", "You are not authorized to access this page.");
                                break;
                            case 404:
                                Utils.alertDialogHandler(getContext(), "Error", "The page you are looking for does not exist.");
                                break;
                            case 500:
                                Utils.alertDialogHandler(getContext(), "Error", "Server error. Please try again later.");
                                break;
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<IContact> call, @NonNull Throwable t) {
                        Utils.alertDialogHandler(getContext(), "Error", t.getMessage());
                    }
                });
            } else {
                getErrorMessage();
            }
        });
    }

    /**
     * If the reason is not selected, the error message will be displayed. If the message is empty, the
     * error message will be displayed. If the reason is not selected and the message is empty, the
     * error message will be displayed
     */
    public void getErrorMessage() {
        if (intReason != 0) {
            binding.tvErrorMessage.setVisibility(View.GONE);
        } else {
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(R.string.ReasEmpty);
        }

        if (message.isEmpty()){
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(R.string.messEmpty);
        }

        if (intReason == 0 && message.isEmpty()){
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(R.string.allEmpty);
        }

    }
}