package com.example.doggo_android.Adapters.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Interfaces.adminUserDocItemClickListener;
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.Models.IUserDocument;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.RvItemDocumentMemberBinding;

public class AdminUserDocsViewHolder extends RecyclerView.ViewHolder {

    private RvItemDocumentMemberBinding binding;

    public AdminUserDocsViewHolder(RvItemDocumentMemberBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(IUserDocument documentDisplay,adminUserDocItemClickListener listener) {
        if (documentDisplay.getRequiredDocument() == null){
            return;
        }
        binding.ItemRecyclerViewDocumentName.setText(documentDisplay.getRequiredDocument().getTitle());
        if (documentDisplay.getStatus() == DOC_STATUS.NOT_SENT) {
            binding.ItemRecyclerViewDocumentStatus.setText("Pas encore envoyé");
            binding.ItemRecyclerViewLogoStatus.setImageResource(R.drawable.ic_baseline_more_horiz_24);
        } else if (documentDisplay.getStatus() == DOC_STATUS.PENDING) {
            binding.ItemRecyclerViewDocumentStatus.setText("En attente");
            binding.ItemRecyclerViewDocumentStatus.setTextColor(itemView.getResources().getColor(R.color.light_orange));
            binding.ItemRecyclerViewLogoStatus.setImageResource(R.drawable.ic_baseline_priority_high_24);
        } else if (documentDisplay.getStatus() == DOC_STATUS.ACCEPTED) {
            binding.ItemRecyclerViewDocumentStatus.setText("Accepté");
            binding.ItemRecyclerViewLogoStatus.setImageResource(R.drawable.ic_baseline_check_24);
        } else if (documentDisplay.getStatus() == DOC_STATUS.REJECTED) {
            binding.ItemRecyclerViewDocumentStatus.setText("Refusé");
            binding.ItemRecyclerViewLogoStatus.setImageResource(R.drawable.ic_baseline_close_24);
        }
        binding.getRoot().setOnClickListener(v -> listener.onRvItemClick(documentDisplay));
    }

    public static AdminUserDocsViewHolder create(ViewGroup parent) {
        RvItemDocumentMemberBinding binding = RvItemDocumentMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminUserDocsViewHolder(binding);
    }
}
