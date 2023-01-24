package com.example.doggo_android.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doggo_android.Adapters.AdminUserDocsDownloadAdapter;
import com.example.doggo_android.Interfaces.adminUserDownloadItemClicListener;
import com.example.doggo_android.Models.IUserDocument;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.ViewModels.DocumentViewModel;
import com.example.doggo_android.databinding.FragmentAdminUserDocumentZoomBinding;

import java.io.File;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminUserDocumentZoomFragment extends Fragment {

    FragmentAdminUserDocumentZoomBinding binding;
    IUserDocument document;
    DocumentViewModel documentViewModel;

    public AdminUserDocumentZoomFragment() {
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
        binding = FragmentAdminUserDocumentZoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        documentViewModel = new ViewModelProvider(requireActivity()).get(DocumentViewModel.class);
        document = documentViewModel.getSelectedUser();

        binding.TVDocumentTitle.setText(document.getRequiredDocument().getTitle());
        binding.TVDocumentDescription.setText(document.getRequiredDocument().getDescription());

        AdminUserDocsDownloadAdapter adapter = new AdminUserDocsDownloadAdapter(document.getDocumentUrl(), url -> {
            Uri uri = Uri.parse(url);
            Log.d("Download", "onDocumentPreviewClick: " + uri);
            String downloadLocation = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + url.substring(url.lastIndexOf("/")+1);

            downloadFile(uri.toString(), downloadLocation, url.substring(url.lastIndexOf("/")+1), "image/jpeg", requireContext());
        });

        binding.recyclerViewDocumentFiles.setAdapter(adapter);
        binding.recyclerViewDocumentFiles.setHasFixedSize(true);
        binding.recyclerViewDocumentFiles.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.ButtonApprove.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Approuver document?");
            builder.setMessage("Voulez-vous vraiment approuver ce document?\nVous pourrez les refuser plus tard.");
            builder.setPositiveButton("Oui",(dialog,which) -> approveDocument());
            builder.setNegativeButton("Non",(dialog,which) -> dialog.cancel());
            builder.show();
        });

        binding.ButtonRefuse.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Refuser document?");
            builder.setMessage("Voulez-vous vraiment refuser ce document?\nVeuillez mettre une raison ci dessous");
            final EditText input = new EditText(requireContext());
            builder.setView(input);
            builder.setPositiveButton("Oui", ((dialog, which) -> {
                if (input.getText().toString().isBlank()){
                    Toast.makeText(requireContext(),"Veuillez mettre une raison!", Toast.LENGTH_SHORT);
                } else {
                    rejectDocument(input.getText().toString());
                }
            }));

            builder.setNegativeButton("Non", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    public void downloadFile(String url, String filepath, String title, String desc, Context context) {
        if (new File(filepath).exists()) {
            Toast.makeText(context, "This file is already downloaded.", Toast.LENGTH_LONG).show();
        } else {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            // in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(title);
                request.setDescription(desc);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filepath);
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            manager.enqueue(request);
            final String finalDestination = filepath;
            final BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {

                    //Do what to do after the download completes
                }
            };
            context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            Toast.makeText(context, "Download Started,check downloads when completed.", Toast.LENGTH_LONG).show();
        }
    }

    public void approveDocument(){

        HashMap<String, String> map = new HashMap<>();
        map.put("status", "approved");

        updateDocument(map);
    }

    public void rejectDocument(String reason){

        HashMap<String, String> map = new HashMap<>();
        map.put("status", "rejected");
        map.put("rejectionReason", reason);

        updateDocument(map);
    }

    public void updateDocument(HashMap<String,String> map){
        RetrofitRequests retrofitRequests = Utils.getRetrofitCon(requireContext());
        String token = Utils.getToken(requireContext());

        Call<IUserDocument> call = retrofitRequests.executeUpdateDoc(document.getId(), map, token);

        call.enqueue(new Callback<IUserDocument>() {
            @Override
            public void onResponse(Call<IUserDocument> call, Response<IUserDocument> response) {
                if (response.isSuccessful()){
                    Toast.makeText(requireContext(), "Document mis à jour", Toast.LENGTH_SHORT).show();
                    documentViewModel.updateDocumentAdminList(documentViewModel.getSelectedUser(), response.body());
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireContext(), "Erreur lors de la mise à jour du document", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IUserDocument> call, Throwable t) {
                Toast.makeText(requireContext(), "Erreur lors de la mise à jour du document", Toast.LENGTH_SHORT).show();
            }
        });
    }
}