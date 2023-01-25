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
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.R;
import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Interfaces.documentZoomClickListener;
import com.example.doggo_android.ViewModels.DocumentViewModel;
import com.example.doggo_android.databinding.RvItemDocumentMemberBinding;

import java.util.List;

public class DocumentMemberAdapter extends RecyclerView.Adapter<DocumentMemberAdapter.DocumentViewHolder> {

    List<DocumentDisplay> documentDisplays;
    Context context;

    DocumentViewModel viewModelDocument;

    RvItemDocumentMemberBinding binding;

    documentZoomClickListener listener;
    
    public DocumentMemberAdapter(List<DocumentDisplay> documentDisplays, Context context, documentZoomClickListener listener) {
        this.documentDisplays = documentDisplays;
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
            layout = binding.ItemRecyclerViewDocumentLayout;
        }
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = RvItemDocumentMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DocumentViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, int position) {
        holder.name.setText(documentDisplays.get(position).getName());
        if (documentDisplays.get(position).getStatus() == DOC_STATUS.NOT_SENT) {
            holder.statusDescription.setText("Pas encore envoyé");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_more_horiz_24);
        } else if (documentDisplays.get(position).getStatus() == DOC_STATUS.PENDING) {
            holder.statusDescription.setText("En attente");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_more_horiz_24);
        } else if (documentDisplays.get(position).getStatus() == DOC_STATUS.ACCEPTED) {
            holder.statusDescription.setText("Accepté");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_check_24);
        } else if (documentDisplays.get(position).getStatus() == DOC_STATUS.REJECTED) {
            holder.statusDescription.setText("Refusé");
            holder.statusLogo.setImageResource(R.drawable.ic_baseline_close_24);
        }

        holder.layout.setOnClickListener(v -> {
            listener.onDocumentZoomClick(documentDisplays.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return documentDisplays.size();
    }

}
