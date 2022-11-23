package com.example.doggo_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Models.IActus;
import com.example.doggo_android.databinding.FragmentHomeBinding;
import com.example.doggo_android.databinding.RvActualitesItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ActualitesAdapter extends RecyclerView.Adapter<ActualitesAdapter.ActualitesViewHolder> {

    Context context;
    ArrayList<IActus> actualites;

    public ActualitesAdapter(Context context, ArrayList<IActus> actualites) {
        this.context = context;
        this.actualites = actualites;
    }

    @Override
    public ActualitesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvActualitesItemBinding binding = RvActualitesItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ActualitesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ActualitesViewHolder holder, int position) {
        IActus actualite = actualites.get(position);
        holder.binding.rvActualitesTopTitle.setText(actualite.getTitle());
        holder.binding.rvActualitesBottomDescription.setText(actualite.getDescription());


    }

    @Override
    public int getItemCount() {
        return actualites.size();
    }

    public class ActualitesViewHolder extends RecyclerView.ViewHolder {

        RvActualitesItemBinding binding;

        public ActualitesViewHolder(RvActualitesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

