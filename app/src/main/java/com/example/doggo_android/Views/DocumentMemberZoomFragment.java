package com.example.doggo_android.Views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doggo_android.Adapters.DocumentPreviewAdapter;
import com.example.doggo_android.Interfaces.documentPreviewClickListener;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentDocumentMemberZoomBinding;
import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.ViewModels.DocumentViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class DocumentMemberZoomFragment extends Fragment {

    FragmentDocumentMemberZoomBinding binding;
    DocumentViewModel viewModelDocument;

    File lastestFile;

    ActivityResultLauncher<Intent> launcherCamera;
    Intent intentCamera;

    ActivityResultLauncher<Intent> launcherExplorer;
    Intent intentExplorer;

    public DocumentMemberZoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcherCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK){
                File fichierDoc = new File(lastestFile.getAbsolutePath());

                Log.d("DOC_SAVE", "Fichier créé a : " + fichierDoc.getAbsolutePath());

                Bitmap thumbnail = null;
                try {
                    thumbnail = getThumbnail(Uri.fromFile(fichierDoc));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                viewModelDocument.getSelectedDocument().getDocumentUrl().put(fichierDoc.getAbsolutePath(),thumbnail);

                binding.recyclerViewDocPreview.getAdapter().notifyDataSetChanged();
                //TODO: envoyer le fichier au serveur

            }
        });
        launcherExplorer = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                String path = result.getData().getData().getPath();
                File fichierDoc = new File(path);
                Log.d("DOC_SAVE", fichierDoc.getName());

                viewModelDocument.getSelectedDocument().getDocumentUrl().put(fichierDoc.getAbsolutePath(),null);

                //TODO: envoyer le fichier au serveur

                binding.recyclerViewDocPreview.getAdapter().notifyDataSetChanged();

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

        DocumentDisplay documentDisplay = viewModelDocument.getSelectedDocument();

        binding.textViewDocumentName.setText(documentDisplay.getName());
        binding.textViewDocumentDescription.setText(documentDisplay.getDescription());
        binding.textViewAddDocument.setText(documentDisplay.getStatus().StatusMessage);

        DocumentPreviewAdapter adapter = new DocumentPreviewAdapter(documentDisplay.getDocumentUrl(), getActivity(), (documentPreviewClickListener) documentUrl -> {

        });

        binding.recyclerViewDocPreview.setAdapter(adapter);
        binding.recyclerViewDocPreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));



        if (documentDisplay.getStatus() == DOC_STATUS.PENDING || documentDisplay.getStatus() == DOC_STATUS.ACCEPTED){
            binding.LayoutAddDocumentButton.setVisibility(View.INVISIBLE);

        }  else if (documentDisplay.getStatus() == DOC_STATUS.REJECTED){
            binding.textViewAddDocumentRejectionReason.setText("Raison pour rejet : " + documentDisplay.getRejectionReason());
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

        binding.buttonAddDocumentGallery.setOnClickListener(v -> {
            intentExplorer = new Intent(Intent.ACTION_GET_CONTENT);
            intentExplorer.setType("*/*");
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                launcherExplorer.launch(intentExplorer);
            } else {
                requestPermissionLauncherExplorer.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.buttonSendDocuments.setOnClickListener(v -> {

        });
    }

    private final ActivityResultLauncher<String> requestPermissionLauncherCamera = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted){
            launcherCamera.launch(intentCamera);
        } else {
            Toast.makeText(requireContext(), "Permission refusée, veuillez modifier les permissions dans vos paramètres", Toast.LENGTH_LONG).show();
        }
    });

    private final ActivityResultLauncher<String> requestPermissionLauncherExplorer = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted){
            launcherExplorer.launch(intentExplorer);
        } else {
            Toast.makeText(requireContext(), "Permission refusée, veuillez modifier les permissions dans vos paramètres", Toast.LENGTH_LONG).show();
        }
    });

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input =  requireContext().getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 200) ? (originalSize / 200) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = requireContext().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
}