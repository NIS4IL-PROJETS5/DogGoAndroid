package com.example.doggodocs.fragment;

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

import com.example.doggodocs.R;
import com.example.doggodocs.adapter.DocumentAdapter;
import com.example.doggodocs.databinding.FragmentDocumentListBinding;
import com.example.doggodocs.model.Document;
import com.example.doggodocs.viewModel.DocumentSharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class DocumentListFragment extends Fragment {

    FragmentDocumentListBinding binding;
    List<Document> documentsValide = new ArrayList<>();
    List<Document> documentsNonValide = new ArrayList<>();

    public DocumentListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentDocumentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DocumentSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(DocumentSharedViewModel.class);
        List<Document> documents = viewModel.getDocumentList();
        DocumentAdapter adapter = new DocumentAdapter(documents, getContext(), document -> {
            viewModel.setSelected(document);
            Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_documentListFragment_to_documentDetailFragment);
        });

        this.binding.listDoc.setAdapter(adapter);
        this.binding.listDoc.setLayoutManager(new LinearLayoutManager(getContext()));
        this.binding.listDoc.setHasFixedSize(true);
        this.binding.buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.listDoc.setAdapter(adapter);
            }
        });

        this.binding.buttonValide.setOnClickListener(new View.OnClickListener() {
            public List<Document> ClickValide(){
                for(int i =0;i<documents.size();i++){
                    if(documents.get(i).isStatut() == true){
                        documentsValide.add(documents.get(i));
                    }
                }
                return documentsValide;
            }
            @Override
            public void onClick(View view) {
                ClickValide();

                DocumentAdapter adapter2 = new DocumentAdapter(documentsValide, getContext(), document -> {
                    viewModel.setSelected(document);
                    Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_documentListFragment_to_documentDetailFragment);
                });

                binding.listDoc.setAdapter(adapter2);
            }
        });


        this.binding.buttonNonValide.setOnClickListener(new View.OnClickListener() {
            public List<Document> ClickNonValide(){
                for(int i =0;i<documents.size();i++){
                    if(documents.get(i).isStatut() == false){
                        documentsNonValide.add(documents.get(i));
                    }
                }
                return documentsNonValide;
            }
            @Override
            public void onClick(View view) {
                ClickNonValide();

                DocumentAdapter adapter3 = new DocumentAdapter(documentsNonValide, getContext(), document -> {
                    viewModel.setSelected(document);
                    Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_documentListFragment_to_documentDetailFragment);
                });

                binding.listDoc.setAdapter(adapter3);
            }
        });

    }
}