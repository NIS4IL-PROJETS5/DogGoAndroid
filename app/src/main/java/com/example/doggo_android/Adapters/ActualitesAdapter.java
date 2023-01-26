package com.example.doggo_android.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.doggo_android.Models.IActuImage;
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
        holder.binding.rvActualitesDate.setText(actualite.getStartDate());
        if (actualite.getImages() != null && actualite.getImages().size() > 0) {
            Log.d("bruh", "onBindViewHolder: YA PHOTO " + actualite.getImages().size());
            holder.binding.rvActualitesMiddleContainer.setVisibility(View.VISIBLE);
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            for (IActuImage image : actualite.getImages()) {
                String comment = "";
                if (image.getActphoCommentaire() != null){
                    comment = image.getActphoCommentaire();
                }
                slideModels.add(new SlideModel(image.getActphoFichierUrl(context), comment, ScaleTypes.CENTER_CROP));
            }
            holder.binding.imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
        }
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

