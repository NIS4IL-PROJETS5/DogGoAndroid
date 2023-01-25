package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.Adapters.AdminUserDocsAdapter;
import com.example.doggo_android.Interfaces.adminUserDocItemClickListener;
import com.example.doggo_android.Models.IUserDocument;
import com.example.doggo_android.R;
import com.example.doggo_android.ViewModels.DocumentViewModel;
import com.example.doggo_android.ViewModels.UserViewModel;
import com.example.doggo_android.databinding.FragmentAdminUserDocumentListBinding;

import java.util.List;


public class AdminUserDocumentListFragment extends Fragment {

    FragmentAdminUserDocumentListBinding binding;

    DocumentViewModel documentViewModel;
    UserViewModel userViewModel;


    public AdminUserDocumentListFragment() {
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
        binding = FragmentAdminUserDocumentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        documentViewModel = new ViewModelProvider(requireActivity()).get(DocumentViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        RecyclerView rv = binding.AdminDocumentListRecyclerView;
        final AdminUserDocsAdapter adapter = new AdminUserDocsAdapter(new AdminUserDocsAdapter.AdminUserDocsDiffCallback(), new adminUserDocItemClickListener() {
            @Override
            public void onRvItemClick(IUserDocument userDoc) {
                documentViewModel.setSelectedUser(userDoc);
                Navigation.findNavController(view).navigate(R.id.action_adminUserDocumentListFragment_to_adminUserDocumentZoomFragment);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        documentViewModel.handleGetUserDocumentsForSpecificUser(requireContext(), userViewModel.getSelectedUser().getId());

        documentViewModel.getSelectedUserDocuments().observe(getViewLifecycleOwner(), documents -> {
            adapter.submitList(documents);
        });

    }
}