package com.example.doggodocs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggodocs.databinding.DocumentItemBinding;
import com.example.doggodocs.model.Document;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>{

    List<Document> documents;
    Context context;
    DocumentListener listener;

    public DocumentAdapter(List<Document> documents, Context context, DocumentListener listener) {
        this.documents = documents;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DocumentItemBinding binding =  DocumentItemBinding.inflate(LayoutInflater.from(this.context),parent, false);
        return new DocumentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        final Document document = documents.get(position);
        holder.binding.getRoot().setOnClickListener(view -> listener.onDocumentClicked(document));
        holder.binding.textViewAuteur.setText(document.getAuteur());
        holder.binding.imageViewValiditeLogo.setImageResource(document.getImage());
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder{
        DocumentItemBinding binding;
        public DocumentViewHolder(DocumentItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
