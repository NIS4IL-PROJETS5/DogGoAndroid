package com.example.doggo_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.MainActivity;
import com.example.doggo_android.R;
import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Interfaces.documentZoomClickListener;
import com.example.doggo_android.Models.Document;
import com.example.doggo_android.ViewModels.DocumentViewModel;
import com.example.doggo_android.databinding.RvItemDocumentMemberBinding;

import java.util.List;

public class DocumentMemberAdapter extends RecyclerView.Adapter<DocumentMemberAdapter.DocumentViewHolder> {

    List<Document> documents;
    Context context;

    DocumentViewModel viewModelDocument;

    RvItemDocumentMemberBinding binding;

    documentZoomClickListener listener;
    
    public DocumentMemberAdapter(List<Document> documents, Context context, documentZoomClickListener listener) {
        this.documents = documents;
        this.context = context;
        this.viewModelDocument = new ViewModelProvider((MainActivity)context).get(DocumentViewModel.class);
        this.listener = listener;
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        TextView name, statusDescription;
        ImageView statusLogo;
        ConstraintLayout layout;



        public DocumentViewHolder(@NonNull View itemView, RvItemDocumentMemberBinding binding) {
            super(itemView);
            name = binding.ItemRecyclerViewDocumentName;
            statusDescription = binding.ItemRecyclerViewDocumentStatus;
            statusLogo = binding.ItemRecyclerViewLogoStatus;
        }
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = RvItemDocumentMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DocumentViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, int position) {
        holder.name.setText(documents.get(position).getName());
        if (documents.get(position).getStatus() == DOC_STATUS.NOT_SENT) {
            holder.statusDescription.setText("Pas encore envoyé");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_more_horiz_24);
        } else if (documents.get(position).getStatus() == DOC_STATUS.PENDING) {
            holder.statusDescription.setText("En attente");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_more_horiz_24);
        } else if (documents.get(position).getStatus() == DOC_STATUS.ACCEPTED) {
            holder.statusDescription.setText("Accepté");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_check_24);
        } else if (documents.get(position).getStatus() == DOC_STATUS.REJECTED) {
            holder.statusDescription.setText("Refusé");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_close_24);
        }

        if (documents.get(position).getStatus() == DOC_STATUS.NOT_SENT) {
            holder.statusLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDocumentZoomClick(documents.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

}
