package com.example.doggodocs.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggodocs.R;
import com.example.doggodocs.WebActivity;
import com.example.doggodocs.databinding.FragmentDocumentDetailBinding;
import com.example.doggodocs.model.Document;
import com.example.doggodocs.viewModel.DocumentSharedViewModel;


public class DocumentDetailFragment extends Fragment {

    FragmentDocumentDetailBinding binding;

    public DocumentDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDocumentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DocumentSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(DocumentSharedViewModel.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), item ->{
        binding.textViewAuteur.setText(item.getAuteur());
        binding.textViewDocChien.setText(item.getDocumentChien());
        binding.textViewDocInscription.setText(item.getDocumentInscription());

        binding.textViewDocInscription.setOnClickListener(view1 -> {
            Intent i = new Intent(getContext(), WebActivity.class);
            i.putExtra("pdf_url",item.getLienInscription());
            startActivity(i);
        });

        binding.textViewDocChien.setOnClickListener(view12 -> {
            Intent i = new Intent(getContext(), WebActivity.class);
            i.putExtra("pdf_url",item.getLienChien());
            startActivity(i);
        });

        binding.valider.setOnClickListener(view13 -> {
            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + "subject text"+ "&body=" + "body text " + "&to=" + "boyeralyssa830@mail.com");
            mailIntent.setData(data);
            startActivity(Intent.createChooser(mailIntent, "Send mail..."));
        });

        binding.retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_documentDetailFragment_to_documentListFragment);
            }
        });

        binding.buttonValiderDFinitif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setStatut(true);
                Navigation.findNavController(requireActivity(), R.id.container).navigate(R.id.action_documentDetailFragment_to_documentListFragment);
            }
        });
        });
    }
}