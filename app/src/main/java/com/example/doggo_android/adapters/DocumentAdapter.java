package com.example.doggo_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggo_android.MainActivity;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.RvitemDocumentMemberBinding;
import com.example.doggo_android.enums.DOC_STATUS;
import com.example.doggo_android.models.Document;
import com.example.doggo_android.viewmodels.DocumentViewModel;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    List<Document> documents;
    Context context;

    DocumentViewModel viewModelDocument;

    RvitemDocumentMemberBinding binding;
    
    public DocumentAdapter(List<Document> documents, Context context) {
        this.documents = documents;
        this.context = context;
        this.viewModelDocument = new ViewModelProvider((MainActivity)context).get(DocumentViewModel.class);
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        TextView name, statusDescription;
        ImageView statusLogo;
        ConstraintLayout layout;



        public DocumentViewHolder(@NonNull View itemView, RvitemDocumentMemberBinding binding) {
            super(itemView);
            name = binding.ItemRecyclerViewDocumentName;
            statusDescription = binding.ItemRecyclerViewDocumentStatus;
            statusLogo = binding.ItemRecyclerViewLogoStatus;
            layout = binding.ItemRecyclerViewDocumentLayout;
        }
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = RvitemDocumentMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

        holder.layout.setOnClickListener(v -> {
            viewModelDocument.setSelectedDocument(documents.get(position));
            Navigation.findNavController(holder.itemView).navigate(R.id.action_documentMemberFragment_to_documentMemberFocusFragment);
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

}
