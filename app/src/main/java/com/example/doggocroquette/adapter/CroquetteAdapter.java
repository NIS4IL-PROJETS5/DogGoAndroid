package com.example.doggocroquette.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggocroquette.R;
import com.example.doggocroquette.databinding.CroquetteItemBinding;
import com.example.doggocroquette.model.Croquette;
import com.example.doggocroquette.viewModel.PanierSharedViewModel;

import java.util.List;

public class CroquetteAdapter extends RecyclerView.Adapter<CroquetteAdapter.CroquetteViewHolder>{

    List<Croquette> croquettes;
    Context context;
    CroquetteListener listener;

    public CroquetteAdapter(List<Croquette> croquettes, Context context, CroquetteListener listener) {
        this.croquettes = croquettes;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CroquetteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CroquetteItemBinding binding =  CroquetteItemBinding.inflate(LayoutInflater.from(this.context),parent, false);
        return new CroquetteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CroquetteViewHolder holder, int position) {
        final Croquette croquette = croquettes.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                if(croquette.getStock()>0)
                listener.onCroquetteClicked(croquette);
            }
        });
        holder.binding.textViewDescriptionCroquette.setText(croquette.getDescription());
        holder.binding.textViewNomCroquette.setText(croquette.getNom());
        holder.binding.textViewPrixCroquette.setText(String.valueOf(croquette.getPrix()));
        holder.binding.textViewNbPanierCroquette.setText(String.valueOf(croquette.getNbPanier()));
        holder.binding.buttonAjoutPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanierSharedViewModel panierSharedViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(PanierSharedViewModel.class);
                if(croquette.getStock()>0){
                    holder.binding.buttonEnleverPanier.setEnabled(true);
                    croquette.setNbPanier(croquette.getNbPanier()+1);
                    croquette.setStock(croquette.getStock()-1);
                    holder.binding.textViewNbPanierCroquette.setText(String.valueOf(croquette.getNbPanier()));
                    panierSharedViewModel.addCroquetteToPanier(croquette);
                }

            }
        });
        holder.binding.buttonEnleverPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanierSharedViewModel panierSharedViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(PanierSharedViewModel.class);
                if(croquette.getNbPanier()>0){
                    holder.binding.buttonAjoutPanier.setEnabled(true);
                    croquette.setNbPanier(croquette.getNbPanier()-1);
                    croquette.setStock(croquette.getStock()+1);
                    holder.binding.textViewNbPanierCroquette.setText(String.valueOf(croquette.getNbPanier()));
                    panierSharedViewModel.removeCroquetteFromPanier(croquette);
                }
            }
        });
        if (croquette.getStock() == 0) {
            holder.binding.imageView2.setImageResource(R.drawable.epuise);
            holder.binding.buttonAjoutPanier.setEnabled(false);
        } else {
            holder.binding.imageView2.setVisibility(View.INVISIBLE);
        }
        if(croquette.getNbPanier() == 0){
            holder.binding.buttonEnleverPanier.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return croquettes.size();
    }

    public static class CroquetteViewHolder extends RecyclerView.ViewHolder{
        CroquetteItemBinding binding;
        public CroquetteViewHolder(CroquetteItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}