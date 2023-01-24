package com.example.doggo_android.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.Models.IRequiredDocument;
import com.example.doggo_android.Models.IUserDocument;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class DocumentViewModel extends ViewModel {

    MutableLiveData<ArrayList<IRequiredDocument>> requiredDocuments = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<ArrayList<IUserDocument>> userDocuments = new MutableLiveData<>(new ArrayList<>());

    MutableLiveData<ArrayList<IUserDocument>> SelectedUserDocuments = new MutableLiveData<>(new ArrayList<>());

    List<DocumentDisplay> documentDisplays = new ArrayList<>();
    MutableLiveData<List<DocumentDisplay>> documentDisplaysUsers = new MutableLiveData<>(new ArrayList<>());
    DocumentDisplay selectedDocumentDisplay;
    IUserDocument selectedDocumentUser;

    public DocumentDisplay getSelectedDocument() {
        return selectedDocumentDisplay;
    }

    public void setSelectedDocument(DocumentDisplay selectedDocumentDisplay) {
        this.selectedDocumentDisplay = selectedDocumentDisplay;
    }

    public void setSelectedUser(IUserDocument selectedDocumentDisplayUser) {
        this.selectedDocumentUser = selectedDocumentDisplayUser;
    }

    public IUserDocument getSelectedUser() {
        return selectedDocumentUser;
    }

    public List<DocumentDisplay> getDocuments() {
        return documentDisplays;
    }

    public List<DocumentDisplay> getPendingDocuments(){
        List<DocumentDisplay> documentDisplays = new ArrayList<>();
        for (DocumentDisplay documentDisplay : this.documentDisplays){
            if (documentDisplay.getStatus() == DOC_STATUS.NOT_SENT || documentDisplay.getStatus() == DOC_STATUS.REJECTED){
                documentDisplays.add(documentDisplay);
            }
        }
        return documentDisplays;
    }

    public LiveData<List<DocumentDisplay>> getDocumentsUsers() {
        return documentDisplaysUsers;
    }

    public void setDocuments(List<DocumentDisplay> documentDisplays) {
        this.documentDisplays = documentDisplays;
    }

    public LiveData<ArrayList<IRequiredDocument>> getRequiredDocuments() {
        return requiredDocuments;
    }

    public LiveData<ArrayList<IUserDocument>> getUserDocuments() {
        return userDocuments;
    }

    public void setRequiredDocuments(ArrayList<IRequiredDocument> requiredDocuments) {
        this.requiredDocuments.postValue(requiredDocuments);
    }

    public void setUserDocuments(ArrayList<IUserDocument> userDocuments) {
        this.userDocuments.setValue(userDocuments);
    }

    public void selectedUserDocuments(ArrayList<IUserDocument> userDocuments) {
        this.SelectedUserDocuments.setValue(userDocuments);
    }

    public LiveData<ArrayList<IUserDocument>> getSelectedUserDocuments() {
        return SelectedUserDocuments;
    }

    public void addRequiredDocument(IRequiredDocument requiredDocument) {
        ArrayList<IRequiredDocument> newRequiredDocuments = this.requiredDocuments.getValue();
        newRequiredDocuments.add(requiredDocument);
        this.requiredDocuments.setValue(newRequiredDocuments);
    }

    public void addUserDocument(IUserDocument userDocument) {
        ArrayList<IUserDocument> newUserDocuments = this.userDocuments.getValue();
        newUserDocuments.add(userDocument);
        this.userDocuments.setValue(newUserDocuments);
    }

    public void addAllUserDocument(IUserDocument userDocument) {
        ArrayList<IUserDocument> newUserDocuments = this.SelectedUserDocuments.getValue();
        newUserDocuments.add(userDocument);
        this.SelectedUserDocuments.setValue(newUserDocuments);
    }

    public void updateRequiredDocument(IRequiredDocument requiredDocument) {
        ArrayList<IRequiredDocument> newRequiredDocuments = this.requiredDocuments.getValue();
        for (int i = 0; i < newRequiredDocuments.size(); i++) {
            if (newRequiredDocuments.get(i).getReqDocId() == requiredDocument.getReqDocId()) {
                newRequiredDocuments.set(i, requiredDocument);
                break;
            }
        }
        this.requiredDocuments.setValue(newRequiredDocuments);
    }

    public void deleteRequiredDocument(IRequiredDocument requiredDocument) {
        ArrayList<IRequiredDocument> newRequiredDocuments = this.requiredDocuments.getValue();
        for (int i = 0; i < newRequiredDocuments.size(); i++) {
            if (newRequiredDocuments.get(i).getReqDocId() == requiredDocument.getReqDocId()) {
                newRequiredDocuments.remove(i);
                break;
            }
        }
        this.requiredDocuments.setValue(newRequiredDocuments);
    }

    public void handleGetRequiredDocuments(Context ctx) {
        RetrofitRequests requests = Utils.getRetrofitCon(ctx);
        Call<ArrayList<IRequiredDocument>> call = requests.executeGetAllRequiredDocs(Utils.getToken(ctx));
        call.enqueue(new retrofit2.Callback<ArrayList<IRequiredDocument>>() {
            @Override
            public void onResponse(Call<ArrayList<IRequiredDocument>> call, retrofit2.Response<ArrayList<IRequiredDocument>> response) {
                if (response.isSuccessful()) {
                    setRequiredDocuments(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IRequiredDocument>> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    public void handleGetAllUserDocuments(Context ctx){
        RetrofitRequests requests = Utils.getRetrofitCon(ctx);
        Call<ArrayList<IUserDocument>> call = requests.executeGetAllUserDocs(Utils.getToken(ctx));
        call.enqueue(new retrofit2.Callback<ArrayList<IUserDocument>>() {
            @Override
            public void onResponse(Call<ArrayList<IUserDocument>> call, retrofit2.Response<ArrayList<IUserDocument>> response) {
                if (response.isSuccessful()) {
                    selectedUserDocuments(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IUserDocument>> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }


    public void handleGetUserDocumentsForSpecificUser(Context ctx, String userId){
        RetrofitRequests requests = Utils.getRetrofitCon(ctx);
        Call<ArrayList<IUserDocument>> call = requests.executeGetUserDocsAdmin(Utils.getToken(ctx), userId);

        call.enqueue(new retrofit2.Callback<ArrayList<IUserDocument>>() {
            @Override
            public void onResponse(Call<ArrayList<IUserDocument>> call, retrofit2.Response<ArrayList<IUserDocument>> response) {
                if (response.isSuccessful()) {
                    selectedUserDocuments(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IUserDocument>> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    /*
    public void getDislayDocumentsAdminForUser(Context ctx, String userId){
        List<DocumentDisplay> documentDisplays = new ArrayList<>();
        if (requiredDocuments.getValue().size() == 0){
            handleGetRequiredDocuments(ctx);
        }
        if (AllUserDocuments.getValue().size() == 0) {
            Log.d("BRUH", "getDislayDocumentsAdminForUser: Initialisation" );
            handleGetAllUserDocuments(ctx);
        }
        for (IRequiredDocument docs: requiredDocuments.getValue()){
            DocumentDisplay documentDisplay = new DocumentDisplay(docs.getReqDocId(), docs.getTitle(), docs.getDescription());
            Log.d("BRUH", "getDislayDocumentsAdminForUser: " + AllUserDocuments.getValue().size());
            for (IUserDocument userDocs: AllUserDocuments.getValue()){
                Log.d("DOCUMENTVIEW", "getDislayDocumentsAdminForUser: " + userDocs.getRequiredDocument().getReqDocId() + " " + docs.getReqDocId());
                if (Objects.equals(userDocs.getRequiredDocument().getReqDocId(), docs.getReqDocId()) && userDocs.getUserId().equals(userId)){
                    documentDisplay.setStatus(userDocs.getStatus());
                    documentDisplay.setId(userDocs.getId());
                    for (String url: userDocs.getDocumentUrl()) {
                        documentDisplay.getDocumentUrl().put(url,null);
                    }
                    Log.d("DOCUMENTVIEW", "getDislayDocumentsAdminForUser: " + userDocs.getUserId() + " " + userDocs.getId());
                    break;
                }
            }
            documentDisplays.add(documentDisplay);
        }
        documentDisplaysUsers.postValue(documentDisplays);
    }
    */

    /*
    public void getDislayDocumentsAdminForUser(Context ctx, String userId) {

    }

     */

    public void updateDocumentAdminList(IUserDocument old_doc, IUserDocument new_doc){
        ArrayList<IUserDocument> newUserDocuments = this.SelectedUserDocuments.getValue();
        for (int i = 0; i < newUserDocuments.size(); i++){
            if (newUserDocuments.get(i).getId().equals(old_doc.getId())){
                newUserDocuments.set(i, new_doc);
                break;
            }
        }
        this.SelectedUserDocuments.setValue(newUserDocuments);

    }

    public void HandleAddNewRequireDocument(Context ctx, String title, String description){
        RetrofitRequests requests = Utils.getRetrofitCon(ctx);
        String token = Utils.getToken(ctx);
        HashMap<String,String> body = new HashMap<>();

        body.put("title", title);
        body.put("description", description);

        Call<IRequiredDocument> call = requests.executeCreateReqDoc(body, token);
        call.enqueue(new retrofit2.Callback<IRequiredDocument>() {
            @Override
            public void onResponse(Call<IRequiredDocument> call, retrofit2.Response<IRequiredDocument> response) {
                if (response.isSuccessful()) {
                    addRequiredDocument(response.body());
                }
            }

            @Override
            public void onFailure(Call<IRequiredDocument> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    public void handleUpdateRequiredDocument(Context ctx, IRequiredDocument document){
        RetrofitRequests requests = Utils.getRetrofitCon(ctx);
        String token = Utils.getToken(ctx);
        HashMap<String,String> body = new HashMap<>();

        body.put("title", document.getTitle());
        body.put("description", document.getDescription());

        Call<IRequiredDocument> call = requests.executeUpdateReqDoc(body, token, document.getReqDocId());
        Log.d("BRUH", "handleUpdateRequiredDocument: " + document.getReqDocId());
        call.enqueue(new retrofit2.Callback<IRequiredDocument>() {
            @Override
            public void onResponse(Call<IRequiredDocument> call, retrofit2.Response<IRequiredDocument> response) {
                if (response.isSuccessful()) {
                    updateRequiredDocument(response.body());
                    Log.d("DOCUMENT", "onResponse: " + response.body().getTitle());
                    Log.d("DOCUMENT", "onResponse: " + response.body().getDescription());
                } else {
                    Log.d("DOCUMENT", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IRequiredDocument> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    public void handleDeleteRequiredDocument(Context ctx, IRequiredDocument document){
        RetrofitRequests requests = Utils.getRetrofitCon(ctx);
        String token = Utils.getToken(ctx);
        Call<Void> call = requests.executeDeleteReqDoc(token, document.getReqDocId());
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    deleteRequiredDocument(document);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}
