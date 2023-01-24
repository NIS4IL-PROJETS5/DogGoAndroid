package com.example.doggo_android.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.doggo_android.Adapters.ViewHolders.AdminRequiredDocsViewHolder;
import com.example.doggo_android.Interfaces.adminRequiredDocsClickListener;
import com.example.doggo_android.Models.IRequiredDocument;

public class AdminRequiredDocsAdapter extends ListAdapter<IRequiredDocument, AdminRequiredDocsViewHolder> {

    adminRequiredDocsClickListener listener;

    public AdminRequiredDocsAdapter(@NonNull DiffUtil.ItemCallback<IRequiredDocument> diffCallback, adminRequiredDocsClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminRequiredDocsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AdminRequiredDocsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRequiredDocsViewHolder holder, int position) {
        IRequiredDocument current = getItem(position);
        holder.bind(current, listener);
    }

    public static class AdminRequiredDocsDiffCallback extends DiffUtil.ItemCallback<IRequiredDocument> {

        @Override
        public boolean areItemsTheSame(@NonNull IRequiredDocument oldItem, @NonNull IRequiredDocument newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull IRequiredDocument oldItem, @NonNull IRequiredDocument newItem) {
            if (oldItem.getReqDocId() == null || newItem.getReqDocId() == null) {
                return false;
            } else
                return oldItem.getReqDocId().equals(newItem.getReqDocId());
        }
    }
}
