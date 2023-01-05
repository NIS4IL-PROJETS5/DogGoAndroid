package com.example.doggocroquette.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doggocroquette.R;
import com.example.doggocroquette.adapter.PanierAdapter;
import com.example.doggocroquette.databinding.FragmentCroquetteDetailBinding;
import com.example.doggocroquette.model.Croquette;
import com.example.doggocroquette.viewModel.CroquetteSharedViewModel;
import com.example.doggocroquette.viewModel.PanierSharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class CroquetteDetailFragment extends Fragment {

    FragmentCroquetteDetailBinding binding;

    public CroquetteDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCroquetteDetailBinding .inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PanierSharedViewModel viewModelPanier = new ViewModelProvider(requireActivity()).get(PanierSharedViewModel.class);
        CroquetteSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(CroquetteSharedViewModel.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
        binding.textViewNomCroquetteDetail.setText(item.getNom());
        binding.textViewDescriptionCroquetteDetail.setText(item.getDescription());
        binding.buttonAjouterCroquetteDetail.setOnClickListener(view12 -> {
            if(item.getStock() == 1 ){
                binding.buttonAjouterCroquetteDetail.setEnabled(false);
                binding.buttonAjouterCroquetteDetail.setText("Stock épuisé");
            }
            List<Croquette> croquetteList = viewModelPanier.getCroquettePanier();
            PanierAdapter adapter = new PanierAdapter(croquetteList, getContext());
            String nom = item.getNom();
            String description = item.getDescription();
            int prix = item.getPrix();
            int nbPanier = item.getNbPanier()+1;
            item.setNbPanier(nbPanier);
            int stock = item.getStock()-1;
            item.setStock(stock);
            Croquette croquette = new Croquette(nom,description,prix,nbPanier, stock);
            int pos = viewModelPanier.addCroquetteToPanier(croquette);
            adapter.notifyItemInserted(pos);
            adapter.notifyItemInserted(pos);
        });
        binding.buttonEnleverCroquette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getStock()>0){
                    binding.buttonEnleverCroquette.setEnabled(false);
                }
                List<Croquette> croquetteList = viewModelPanier.getCroquettePanier();
                PanierAdapter adapter = new PanierAdapter(croquetteList, getContext());
                String nom = item.getNom();
                String description = item.getDescription();
                int prix = item.getPrix();
                int nbPanier = item.getNbPanier()-1;
                item.setNbPanier(nbPanier);
                int stock = item.getStock()+1;
                item.setStock(stock);
                Croquette croquette = new Croquette(nom,description,prix,nbPanier, stock);
                int pos = viewModelPanier.removeCroquetteFromPanier(croquette);
                adapter.notifyItemRemoved(pos);
                adapter.notifyItemRemoved(pos);
            }
        });
        binding.buttonRetourCroquetteDetail.setOnClickListener(view1 -> Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_croquetteDetailFragment_to_croquetteListFragment));
        });
    }
}