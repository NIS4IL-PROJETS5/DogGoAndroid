package com.example.doggo_android.Adapters;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.doggo_android.Adapters.ViewHolders.AdminUserDocsViewHolder;
import com.example.doggo_android.Interfaces.adminUserDocItemClickListener;
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.Models.IUserDocument;

public class AdminUserDocsAdapter extends ListAdapter<IUserDocument, AdminUserDocsViewHolder> {

    adminUserDocItemClickListener listener;


    public AdminUserDocsAdapter(@NonNull DiffUtil.ItemCallback<IUserDocument> diffCallback, adminUserDocItemClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminUserDocsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AdminUserDocsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserDocsViewHolder holder, int position) {
        IUserDocument current = getItem(position);
        holder.bind(current, listener);
    }

public static class AdminUserDocsDiffCallback extends DiffUtil.ItemCallback<IUserDocument> {

        @Override
        public boolean areItemsTheSame(@NonNull IUserDocument oldItem, @NonNull IUserDocument newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull IUserDocument oldItem, @NonNull IUserDocument newItem) {
            if (oldItem.getId() == null || newItem.getId() == null) {
                return false;
            } else
                return oldItem.getId().equals(newItem.getId());
        }
    }
}
