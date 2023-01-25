package com.example.doggo_android.Adapters;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Interfaces.adminUserDownloadItemClicListener;
import com.example.doggo_android.databinding.RvItemAdminUserDocBinding;

import java.util.List;

public class AdminUserDocsDownloadAdapter extends RecyclerView.Adapter<AdminUserDocsDownloadAdapter.AdminUserDocsDownloadViewHolder> {

    public List<String> documentUrls;
    private adminUserDownloadItemClicListener listener;

    public AdminUserDocsDownloadAdapter(List<String> documentUrls, adminUserDownloadItemClicListener listener) {
        this.documentUrls = documentUrls;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminUserDocsDownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemAdminUserDocBinding binding = RvItemAdminUserDocBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminUserDocsDownloadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserDocsDownloadViewHolder holder, int position) {
        //get filename from url
        String[] urlParts = documentUrls.get(position).split("/");
        String fileName = urlParts[urlParts.length - 1];

        holder.binding.tvDocumentName.setText(fileName);
        holder.binding.tvDocumentNum.setText("Document n°" + (position + 1));
        holder.binding.getRoot().setOnClickListener(v -> listener.onRvItemClick(documentUrls.get(position)));
    }

    @Override
    public int getItemCount() {
        return documentUrls.size();
    }


    public class AdminUserDocsDownloadViewHolder extends RecyclerView.ViewHolder {

        private RvItemAdminUserDocBinding binding;

        public AdminUserDocsDownloadViewHolder(RvItemAdminUserDocBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
