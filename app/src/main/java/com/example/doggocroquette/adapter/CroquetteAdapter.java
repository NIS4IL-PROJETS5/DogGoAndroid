package com.example.doggocroquette.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggocroquette.databinding.CroquetteItemBinding;
import com.example.doggocroquette.model.Croquette;

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
        holder.binding.getRoot().setOnClickListener(view -> listener.onCroquetteClicked(croquette));
        holder.binding.textViewDescriptionCroquette.setText(croquette.getDescription());
        holder.binding.textViewNomCroquette.setText(croquette.getNom());
        holder.binding.textViewPrixCroquette.setText(String.valueOf(croquette.getPrix()));
        holder.binding.textViewNbPanierCroquette.setText(String.valueOf(croquette.getNbPanier()));
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