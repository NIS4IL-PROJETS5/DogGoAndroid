package com.example.doggo_android.Views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doggo_android.databinding.FragmentDocumentMemberZoomBinding;
import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.models.Document;
import com.example.doggo_android.viewmodels.DocumentViewModel;

import java.io.File;


public class DocumentMemberZoomFragment extends Fragment {

    FragmentDocumentMemberZoomBinding binding;
    DocumentViewModel viewModelDocument;

    File lastestFile;

    ActivityResultLauncher<Intent> launcherCamera;
    Intent intentCamera;

    ActivityResultLauncher<Intent> launcherGallery;
    Intent intentGallery;

    public DocumentMemberZoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK){
                viewModelDocument.getSelectedDocument().setStatus(DOC_STATUS.PENDING);
                File fichierDoc = new File(lastestFile.getAbsolutePath());

                Log.d("DOC_SAVE", "Fichier créé a : " + fichierDoc.getAbsolutePath());
                //TODO: envoyer le fichier au serveur
            }
        });
        launcherGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                viewModelDocument.getSelectedDocument().setStatus(DOC_STATUS.PENDING);


            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentDocumentMemberZoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelDocument = new ViewModelProvider(requireActivity()).get(DocumentViewModel.class);

        Document document = viewModelDocument.getSelectedDocument();

        binding.textViewDocumentName.setText(document.getName());
        binding.textViewDocumentDescription.setText(document.getDescription());
        binding.textViewAddDocument.setText(document.getStatus().StatusMessage);

        if (document.getStatus() == DOC_STATUS.PENDING || document.getStatus() == DOC_STATUS.ACCEPTED){
            binding.LayoutAddDocumentButton.setVisibility(View.INVISIBLE);

        }  else if (document.getStatus() == DOC_STATUS.REJECTED){
            binding.textViewAddDocumentRejectionReason.setText("Raison pour rejet : " + document.getRejectionReason());
        }

        binding.buttonAddDocumentCamera.setOnClickListener(v -> {

            lastestFile = new File(requireContext().getExternalCacheDir().getPath() + System.currentTimeMillis() + ".jpg");
            intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".provider", lastestFile);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            Log.d("URICHECK", "onViewCreated: " + lastestFile.getPath());

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                launcherCamera.launch(intentCamera);
            } else {
                requestPermissionLauncherCamera.launch(Manifest.permission.CAMERA);
            }



        });
    }

    private final ActivityResultLauncher<String> requestPermissionLauncherCamera = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted){
            launcherCamera.launch(intentCamera);
        } else {
            Toast.makeText(requireContext(), "Permission refusée, veuillez modifier les permissions dans vos paramètres", Toast.LENGTH_LONG).show();
        }
    });
}