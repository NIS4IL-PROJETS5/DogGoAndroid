package com.example.doggo_android.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.CameraActivity;
import com.example.doggo_android.MainActivity;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentDocumentMemberFocusBinding;
import com.example.doggo_android.enums.DOC_STATUS;
import com.example.doggo_android.models.Document;
import com.example.doggo_android.utils.CameraUtilClass;
import com.example.doggo_android.viewmodels.DocumentViewModel;

import java.io.File;


public class DocumentMemberFocusFragment extends Fragment {

    DocumentViewModel viewModelDocument;
    Document document;

    FragmentDocumentMemberFocusBinding binding;

    private ActivityResultLauncher<Intent> cameraActivityLauncher;
    private ActivityResultLauncher<Intent> galleryActivityLauncher;


    public DocumentMemberFocusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        cameraActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                /*
                viewModelDocument.getSelectedDocument().setDocumentMember(CameraUtilClass.getLatestFile());
                Log.d("DOC", "Document Rajouté a : " + viewModelDocument.getSelectedDocument().getDocumentMember().getAbsolutePath());


            }
        });
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentDocumentMemberFocusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelDocument = new ViewModelProvider(requireActivity()).get(DocumentViewModel.class);
        document = viewModelDocument.getSelectedDocument();

        binding.textViewDocumentName.setText(document.getName());
        binding.textViewDocumentDescription.setText(document.getDescription());
        binding.textViewAddDocument.setText(document.getStatus().StatusMessage);

        binding.buttonAddDocumentCamera.setOnClickListener(v -> cameraOpen());
        binding.buttonAddDocumentGallery.setOnClickListener(v -> galleryOpen());

        if (document.getStatus() == DOC_STATUS.PENDING || document.getStatus() == DOC_STATUS.ACCEPTED){
            binding.LayoutAddDocumentButton.setVisibility(View.INVISIBLE);

        }  else if (document.getStatus() == DOC_STATUS.REJECTED){
            binding.textViewAddDocumentRejectionReason.setText("Raison pour rejet : " + document.getRejectionReason());
        }
    }


    private void cameraOpen(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        Intent intent = new Intent(getContext(), CameraActivity.class);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, CameraUtilClass.createImageUri(requireActivity()));

        startActivity(intent);
    }

    private void galleryOpen(){

    }


}