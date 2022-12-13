package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentDocumentsBinding;


public class Documents extends Fragment {

    FragmentDocumentsBinding binding;




    public Documents() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.imageViewDoc.setImageResource(R.drawable.justificatif_identite);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDocumentsBinding.inflate(inflater, container, false);
        binding.linearLayoutDocuments.setOnClickListener(v -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            ImageView mImageView = binding.imageViewDoc;
            ViewGroup parent = (ViewGroup) mImageView.getParent();
            if (parent != null) {
                parent.removeView(mImageView);
            }

            mImageView.setImageResource(R.drawable.justificatif_identite);
            mBuilder.setView(mImageView);
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
            mImageView.setOnClickListener(v1 -> {
                // Ajoutez la vue enfant à un autre groupe de vues ici
                mDialog.dismiss();
                //refresh la page
                NavHostFragment.findNavController(this).navigate(R.id.documents);
            });
            //delete white corners
            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        });
        return binding.getRoot();

    }
}