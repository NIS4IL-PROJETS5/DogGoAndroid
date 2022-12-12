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

import com.example.doggo_android.databinding.FragmentCompteBinding;


public class CompteFragment extends Fragment {

    static FragmentCompteBinding binding;

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

        if (checkLogin != null) {
            Log.d("TAG", "checkLogin: " + checkLogin);
            binding.fragmentContainerViewConnexion.setVisibility(View.GONE);
            binding.fragmentContainerViewProfile.setVisibility(View.VISIBLE);
        } else{
            binding.fragmentContainerViewConnexion.setVisibility(View.VISIBLE);
            binding.fragmentContainerViewProfile.setVisibility(View.GONE);
        }
    }
}

