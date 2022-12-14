package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.Adapters.PanierAdapter;
import com.example.doggo_android.Models.Panier;
import com.example.doggo_android.R;
import com.example.doggo_android.ViewModels.PanierSharedViewModel;
import com.example.doggo_android.databinding.FragmentPanierListBinding;

import java.util.List;

public class PanierListFragment extends Fragment {

    FragmentPanierListBinding binding;

    public PanierListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentPanierListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PanierSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(PanierSharedViewModel.class);
        List<Panier> paniers = viewModel.getCroquettePanier();
        PanierAdapter adapter = new PanierAdapter(paniers, getContext());
        this.binding.listPanier.setAdapter(adapter);
        this.binding.listPanier.setLayoutManager(new LinearLayoutManager(getContext()));
        this.binding.listPanier.setHasFixedSize(true);
    }
}