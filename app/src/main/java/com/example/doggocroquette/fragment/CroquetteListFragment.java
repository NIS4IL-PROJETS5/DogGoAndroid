package com.example.doggocroquette.fragment;

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

import com.example.doggocroquette.R;
import com.example.doggocroquette.adapter.CroquetteAdapter;
import com.example.doggocroquette.databinding.FragmentCroquetteListBinding;
import com.example.doggocroquette.model.Croquette;
import com.example.doggocroquette.viewModel.CroquetteSharedViewModel;

import java.util.List;

public class CroquetteListFragment extends Fragment {

    FragmentCroquetteListBinding binding;

    public CroquetteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentCroquetteListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CroquetteSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(CroquetteSharedViewModel.class);
        List<Croquette> croquettes = viewModel.getCroquettes();
        CroquetteAdapter adapter = new CroquetteAdapter(croquettes, getContext(), croquette -> {
            viewModel.setSelected(croquette);
            Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_croquetteListFragment_to_croquetteDetailFragment);
        });
        this.binding.imageViewCroquetteToPanier.setOnClickListener(view1 -> Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_croquetteListFragment_to_panierListFragment));
        this.binding.listCroquette.setAdapter(adapter);
        this.binding.listCroquette.setLayoutManager(new LinearLayoutManager(getContext()));
        this.binding.listCroquette.setHasFixedSize(true);
    }
}