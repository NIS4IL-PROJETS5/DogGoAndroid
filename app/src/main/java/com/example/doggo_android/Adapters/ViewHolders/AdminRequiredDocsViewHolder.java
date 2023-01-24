package com.example.doggo_android.Adapters.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Interfaces.adminRequiredDocsClickListener;
import com.example.doggo_android.Models.IRequiredDocument;
import com.example.doggo_android.databinding.RvItemAdminRequiredDocBinding;

public class AdminRequiredDocsViewHolder extends RecyclerView.ViewHolder {

    private RvItemAdminRequiredDocBinding binding;

    public AdminRequiredDocsViewHolder(RvItemAdminRequiredDocBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public static AdminRequiredDocsViewHolder create(ViewGroup parent) {
        RvItemAdminRequiredDocBinding binding = RvItemAdminRequiredDocBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminRequiredDocsViewHolder(binding);
    }

    public void bind(IRequiredDocument requiredDocument, adminRequiredDocsClickListener listener){
        binding.tvRequiredDocumentName.setText(requiredDocument.getTitle());
        binding.imageButtonModifyReqDoc.setOnClickListener(v -> listener.onModifyClick(requiredDocument));
        binding.imageButtonDeleteReqDoc.setOnClickListener(v -> listener.onDeleteClick(requiredDocument));
    }
}
