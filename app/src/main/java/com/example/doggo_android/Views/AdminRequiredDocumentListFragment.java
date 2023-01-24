package com.example.doggo_android.Views;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doggo_android.Adapters.AdminRequiredDocsAdapter;
import com.example.doggo_android.Interfaces.adminRequiredDocsClickListener;
import com.example.doggo_android.Models.IRequiredDocument;
import com.example.doggo_android.ViewModels.DocumentViewModel;
import com.example.doggo_android.databinding.DialogAdminModifyRequiredDocumentBinding;
import com.example.doggo_android.databinding.FragmentAdminRequiredDocumentListBinding;

public class AdminRequiredDocumentListFragment extends Fragment {

    FragmentAdminRequiredDocumentListBinding binding;
    DocumentViewModel documentViewModel;

    public AdminRequiredDocumentListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminRequiredDocumentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        documentViewModel = new ViewModelProvider(this).get(DocumentViewModel.class);

        documentViewModel.handleGetRequiredDocuments(requireContext());

        binding.requireDocsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.requireDocsRecyclerView.setAdapter(new AdminRequiredDocsAdapter(new AdminRequiredDocsAdapter.AdminRequiredDocsDiffCallback(), new adminRequiredDocsClickListener() {
            @Override
            public void onModifyClick(IRequiredDocument document) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                DialogAdminModifyRequiredDocumentBinding dialogBinding = DialogAdminModifyRequiredDocumentBinding.inflate(LayoutInflater.from(requireContext()));
                builder.setView(dialogBinding.getRoot());
                builder.setTitle("Modifier document");

                dialogBinding.editTextDocumentDescription.setText(document.getDescription());
                dialogBinding.editTextDocumentName.setText(document.getTitle());

                builder.setPositiveButton("Modifier", (dialog, which) -> {
                    if (dialogBinding.editTextDocumentDescription.getText().toString().isBlank() || dialogBinding.editTextDocumentName.getText().toString().isBlank()) {
                        Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    } else {
                        document.setTitle(dialogBinding.editTextDocumentName.getText().toString());
                        document.setDescription(dialogBinding.editTextDocumentDescription.getText().toString());
                        documentViewModel.handleUpdateRequiredDocument(requireContext(),document);
                        Toast.makeText(requireContext(), "Document modifié", Toast.LENGTH_SHORT).show();
                        documentViewModel.handleGetRequiredDocuments(requireContext());
                    }
                });
                builder.setNegativeButton("Annuler", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            }

            @Override
            public void onDeleteClick(IRequiredDocument document) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Supprimer document");
                builder.setMessage("Voulez-vous vraiment supprimer ce document ?\nCela supprimera aussi tous les documents utilisateurs liés à ce document");
                builder.setPositiveButton("Supprimer", (dialog, which) -> {
                    documentViewModel.handleDeleteRequiredDocument(requireContext(),document);
                    Toast.makeText(requireContext(), "Document supprimé", Toast.LENGTH_SHORT).show();
                    documentViewModel.handleGetRequiredDocuments(requireContext());
                });
                builder.setNegativeButton("Annuler", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            }
        }));

        binding.floatingActionButtonAddDocument.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            DialogAdminModifyRequiredDocumentBinding dialogBinding = DialogAdminModifyRequiredDocumentBinding.inflate(LayoutInflater.from(requireContext()));
            builder.setView(dialogBinding.getRoot());
            builder.setTitle("Ajouter document");

            builder.setPositiveButton("Ajouter", (dialog, which) -> {
                if (dialogBinding.editTextDocumentDescription.getText().toString().isBlank() || dialogBinding.editTextDocumentName.getText().toString().isBlank()) {
                    Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    documentViewModel.HandleAddNewRequireDocument(requireContext(),dialogBinding.editTextDocumentName.getText().toString(),dialogBinding.editTextDocumentDescription.getText().toString());
                    Toast.makeText(requireContext(), "Document ajouté", Toast.LENGTH_SHORT).show();
                    documentViewModel.handleGetRequiredDocuments(requireContext());
                }
            });
            builder.setNegativeButton("Annuler", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        documentViewModel.getRequiredDocuments().observe(getViewLifecycleOwner(), iRequiredDocuments -> {
            ((AdminRequiredDocsAdapter) binding.requireDocsRecyclerView.getAdapter()).submitList(iRequiredDocuments);
        });
    }
}