package com.example.doggo_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Models.Panier;
import com.example.doggo_android.databinding.RvPanierItemBinding;

import java.util.List;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.PanierViewHolder>{
    List<Panier> paniers;
    Context context;

    public PanierAdapter(List<Panier> paniers, Context context) {
        this.paniers = paniers;
        this.context = context;
    }

    @NonNull
    @Override
    public PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvPanierItemBinding binding =  RvPanierItemBinding.inflate(LayoutInflater.from(this.context),parent, false);
        return new PanierViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierViewHolder holder, int position) {
        final Panier panier = paniers.get(position);
        holder.binding.textViewDescriptionPanier.setText(panier.getDescription());
        holder.binding.textViewNomPanier.setText(panier.getNom());
        holder.binding.textViewPrixPanier.setText(String.valueOf(panier.getPrix()));
    }

    @Override
    public int getItemCount() {
        return paniers.size();
    }

    public static class PanierViewHolder extends RecyclerView.ViewHolder{
        RvPanierItemBinding binding;
        public PanierViewHolder(RvPanierItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
