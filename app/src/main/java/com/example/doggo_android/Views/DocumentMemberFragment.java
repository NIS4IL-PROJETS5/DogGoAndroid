package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.R;
import com.example.doggo_android.Adapters.DocumentMemberAdapter;
import com.example.doggo_android.databinding.FragmentDocumentMemberBinding;
import com.example.doggo_android.ViewModels.DocumentViewModel;


public class DocumentMemberFragment extends Fragment {


    DocumentMemberAdapter documentMemberAdapter;
    DocumentViewModel viewModelDocument;

    FragmentDocumentMemberBinding binding;

    public DocumentMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentDocumentMemberBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelDocument = new ViewModelProvider(requireActivity()).get(DocumentViewModel.class);
        documentMemberAdapter = new DocumentMemberAdapter(viewModelDocument.getDocuments(), getContext(), document -> {
            viewModelDocument.setSelectedDocument(document);
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_candidatureFormFragment);
        });
        binding.DocumentMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.DocumentMemberRecyclerView.setAdapter(documentMemberAdapter);
        binding.DocumentMemberRecyclerView.setHasFixedSize(true);
    }
}