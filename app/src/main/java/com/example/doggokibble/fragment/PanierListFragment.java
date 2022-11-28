package com.example.doggokibble.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggokibble.R;
import com.example.doggokibble.adapter.CroquetteAdapter;
import com.example.doggokibble.databinding.FragmentCroquetteListBinding;
import com.example.doggokibble.databinding.FragmentPanierListBinding;
import com.example.doggokibble.model.Croquette;
import com.example.doggokibble.viewModel.CroquetteSharedViewModel;

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

}