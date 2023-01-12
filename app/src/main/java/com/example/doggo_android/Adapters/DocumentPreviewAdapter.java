package com.example.doggo_android.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.Interfaces.documentPreviewClickListener;
import com.example.doggo_android.Interfaces.documentZoomClickListener;
import com.example.doggo_android.MainActivity;
import com.example.doggo_android.ViewModels.DocumentViewModel;
import com.example.doggo_android.databinding.RvDocpreviewItemBinding;

import java.util.HashMap;

public class DocumentPreviewAdapter extends RecyclerView.Adapter<DocumentPreviewAdapter.DocumentPreviewViewHolder> {

    HashMap<String, Bitmap> documentDisplaysHashMap;
    Context context;

    DocumentViewModel viewModelDocument;

    RvDocpreviewItemBinding binding;

    documentPreviewClickListener listener;

    public DocumentPreviewAdapter(HashMap<String, Bitmap> documentDisplays, Context context, documentPreviewClickListener listener) {
        this.documentDisplaysHashMap = documentDisplays;
        this.context = context;
        this.viewModelDocument = new ViewModelProvider((MainActivity)context).get(DocumentViewModel.class);
        this.listener = listener;
    }

    @NonNull
    @Override
    public DocumentPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RvDocpreviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DocumentPreviewViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentPreviewViewHolder holder, int position) {
        String key = (String) documentDisplaysHashMap.keySet().toArray()[position];
        if (key.length() > 40){
            holder.name.setText("..." + key.substring(key.length()-20));
        } else {
            holder.name.setText(key);
        }
        if (documentDisplaysHashMap.get(key) != null){
            holder.docPreview.setImageBitmap(documentDisplaysHashMap.get(key));
        }
    }

    @Override
    public int getItemCount() {
        return documentDisplaysHashMap.size();
    }

    public static class DocumentPreviewViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView docPreview;

        public DocumentPreviewViewHolder(@NonNull View itemView, RvDocpreviewItemBinding binding) {
            super(itemView);
            name = binding.tvFilename;
            docPreview = binding.ivFilepreview;
        }

    }





}
