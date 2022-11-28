package com.example.doggocroquette.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggocroquette.databinding.PanierItemBinding;
import com.example.doggocroquette.model.Croquette;

import java.util.List;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.PanierViewHolder>{
    List<Croquette> croquettes;
    Context context;

    public PanierAdapter(List<Croquette> croquettes, Context context) {
        this.croquettes = croquettes;
        this.context = context;
    }

    @NonNull
    @Override
    public PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PanierItemBinding binding =  PanierItemBinding.inflate(LayoutInflater.from(this.context),parent, false);
        return new PanierViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierViewHolder holder, int position) {
        final Croquette croquette = croquettes.get(position);
        holder.binding.textViewDescriptionPanier.setText(croquette.getDescription());
        holder.binding.textViewNomPanier.setText(croquette.getNom());
        holder.binding.textViewPrixPanier.setText(String.valueOf(croquette.getPrix()));
    }

    @Override
    public int getItemCount() {
        return croquettes.size();
    }

    public static class PanierViewHolder extends RecyclerView.ViewHolder{
        PanierItemBinding binding;
        public PanierViewHolder(PanierItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}

