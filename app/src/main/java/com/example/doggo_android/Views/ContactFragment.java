package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentContactBinding;
import com.example.doggo_android.databinding.FragmentProfileBinding;


public class ContactFragment extends Fragment {

    FragmentContactBinding binding;
    private String message;


    public ContactFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sendFormContact.setOnClickListener(v -> {
            message = binding.ptMessage.getText().toString();
            Log.d("TEST", "onViewCreated: " + message);
        });
    }
}