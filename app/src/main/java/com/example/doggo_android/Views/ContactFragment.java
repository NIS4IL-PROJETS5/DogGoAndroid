package com.example.doggo_android.Views;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentContactBinding;


public class ContactFragment extends Fragment{

    FragmentContactBinding binding;
    private String message;
    private String reason;



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

        binding.sendFormContact.setOnClickListener(v -> {
            message = binding.ptMessage.getText().toString();
            reason = binding.spinnerChoiceReason.getSelectedItem().toString();
            if (!reason.equals("Choisir une raison") && !message.isEmpty()) {
                Log.d("TEST", "Raison: " + reason);
                Log.d("TEST", "Message: " + message);
                Log.d("TEST", "Token: " + getToken.getString("token", "No token Value"));
                binding.tvErrorMessage.setVisibility(View.GONE);
            }else {
                getErrorMessage();
            }
        });
    }

    public void getErrorMessage() {

        message = binding.ptMessage.getText().toString();
        reason = binding.spinnerChoiceReason.getSelectedItem().toString();

        if (!reason.equals("Choisir une raison")){
            binding.tvErrorMessage.setVisibility(View.GONE);
        } else {
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(R.string.ReasEmpty);
        }

        if (message.isEmpty()){
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(R.string.messEmpty);
        }

        if (reason.equals("Choisir une raison") && message.isEmpty()){
            binding.tvErrorMessage.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText(R.string.allEmpty);
        }

    }
}