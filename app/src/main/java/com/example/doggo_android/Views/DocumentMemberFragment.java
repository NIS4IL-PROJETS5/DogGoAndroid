package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.Models.IRequiredDocument;
import com.example.doggo_android.Models.IUserDocument;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Adapters.DocumentMemberAdapter;
import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentDocumentMemberBinding;
import com.example.doggo_android.ViewModels.DocumentViewModel;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocumentMemberFragment extends Fragment {

    DocumentMemberAdapter documentMemberAdapterFull;
    DocumentMemberAdapter documentMemberAdapterPartial;
    DocumentViewModel viewModelDocument;
    RetrofitRequests requests;
    String token;

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

        this.requests = Utils.getRetrofitCon(requireContext());
        this.token = Utils.getToken(requireContext());

        viewModelDocument = new ViewModelProvider(requireActivity()).get(DocumentViewModel.class);
        documentMemberAdapterFull = new DocumentMemberAdapter(viewModelDocument.getDocuments(), getContext(), document -> {
            viewModelDocument.setSelectedDocument(document);
            Navigation.findNavController(view).navigate(R.id.action_documentMemberFragment_to_documentMemberZoomFragment);
        });

        documentMemberAdapterPartial = new DocumentMemberAdapter(viewModelDocument.getPendingDocuments(), getContext(), document -> {
            viewModelDocument.setSelectedDocument(document);
            Navigation.findNavController(view).navigate(R.id.action_documentMemberFragment_to_documentMemberZoomFragment);
        });


        binding.DocumentMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.DocumentMemberRecyclerView.setAdapter(documentMemberAdapterFull);
        binding.DocumentMemberRecyclerView.setHasFixedSize(true);

        binding.switch1.setOnClickListener(view1 -> {
            if (binding.switch1.isChecked()) {
                binding.DocumentMemberRecyclerView.setAdapter(documentMemberAdapterPartial);
            } else {
                binding.DocumentMemberRecyclerView.setAdapter(documentMemberAdapterFull);
            }
        });

        handleGetRequiredDocuments();



    }

    private void handleGetRequiredDocuments(){
        Call<ArrayList<IRequiredDocument>> call = requests.executeGetUserDocsAndRequiredDocs(token);

        call.enqueue(new Callback<ArrayList<IRequiredDocument>>() {
            @Override
            public void onResponse(Call<ArrayList<IRequiredDocument>> call, Response<ArrayList<IRequiredDocument>> response) {
                Log.d("Retrofit", "onResponse: "+ response.code());
                if (response.isSuccessful() && viewModelDocument.getDocuments().size() == 0) {

                    for (IRequiredDocument document : response.body()) {

                        DocumentDisplay documentDisplay = new DocumentDisplay(
                                document.getReqDocId(),
                                document.getTitle(),
                                document.getDescription(),
                                document.getStatus()
                        );

                        if (documentDisplay.getStatus().equals(DOC_STATUS.REJECTED)) {
                            documentDisplay.setRejectionReason(document.getRejectionReason());
                        }

                        viewModelDocument.getDocuments().add(documentDisplay);

                    }


                    handleGetUserDocuments();
                    documentMemberAdapterFull.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<IRequiredDocument>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors de la récupération des documents", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleGetUserDocuments(){
        Call<ArrayList<IUserDocument>> call = requests.executeGetUserDocs(token);

        call.enqueue(new Callback<ArrayList<IUserDocument>>() {
                @Override
                public void onResponse(Call<ArrayList<IUserDocument>> call, Response<ArrayList<IUserDocument>> response) {
                    Log.d("Retrofit-userdocs", "onResponse: "+ response.code());
                    if (response.isSuccessful()) {

                        for(DocumentDisplay document: viewModelDocument.getDocuments() ){
                            for(IUserDocument userDocument: response.body()){
                                if (userDocument.getRequiredDocument() != null) {
                                    Log.d("document.id", document.getId());
                                    Log.d("userDocument.id", userDocument.getId());
                                    if (Objects.equals(userDocument.getId(), document.getId())){
                                        for (String url: userDocument.getDocumentUrl()){
                                            document.getDocumentUrl().put(url, null);
                                        }
                                    }
                                }
                            }
                        }

                    }

                }



                @Override
                public void onFailure(Call<ArrayList<IUserDocument>> call, Throwable t) {
                    Log.d("FAIL RETROFIT","onFailure: " );
                    Toast.makeText(getContext(), "Erreur lors de la récupération des documents", Toast.LENGTH_LONG).show();
                }
            });
    }

}