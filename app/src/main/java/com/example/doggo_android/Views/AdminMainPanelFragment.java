package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentAdminMainPanelBinding;

public class AdminMainPanelFragment extends Fragment {

    FragmentAdminMainPanelBinding binding;

    public AdminMainPanelFragment() {
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
        binding = FragmentAdminMainPanelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonUserDocs.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_adminMainPanelFragment_to_adminUserListFragment);
        });

        binding.buttonReqDocs.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_adminMainPanelFragment_to_adminRequiredDocumentListFragment);
        });
    }
}