package com.example.doggo_android.Adapters.ViewHolders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Interfaces.adminUserItemClickListener;
import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.RvUserlistItemBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private final RvUserlistItemBinding binding;

    private UserViewHolder(RvUserlistItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(IUser user, adminUserItemClickListener listener) {
        binding.UserItemName.setText(user.getName() + " " + user.getSurname());
        if (user.getDoc_status() != null) {
            if (user.getDoc_status() == DOC_STATUS.ACCEPTED) {
                binding.UserItemDocState.setText("Documents acceptés");
                binding.UserItemDocState.setTextColor(itemView.getResources().getColor(R.color.green));
            } else if (user.getDoc_status() == DOC_STATUS.REJECTED) {
                binding.UserItemDocState.setText("Documents refusés");
                binding.UserItemDocState.setTextColor(itemView.getResources().getColor(R.color.red));
            } else if (user.getDoc_status() == DOC_STATUS.PENDING) {
                binding.UserItemDocState.setText("Documents en attente");
                binding.UserItemDocState.setTextColor(itemView.getResources().getColor(R.color.light_orange));
            } else {
                binding.UserItemDocState.setText("Documents non envoyés");
                binding.UserItemDocState.setTextColor(itemView.getResources().getColor(R.color.grey));
            }
        } else {
            binding.UserItemDocState.setText("Chargement...");
        }
        binding.getRoot().setOnClickListener(v -> listener.onRvItemClick(user));

    }

    public static UserViewHolder create(ViewGroup parent) {
        RvUserlistItemBinding binding = RvUserlistItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

}