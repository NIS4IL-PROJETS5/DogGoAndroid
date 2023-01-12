package com.example.doggo_android.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.DocumentDisplay;
import com.example.doggo_android.Models.IRequiredDocument;
import com.example.doggo_android.Models.IUserDocument;

import java.util.ArrayList;
import java.util.List;

public class DocumentViewModel extends ViewModel {

    MutableLiveData<ArrayList<IRequiredDocument>> requiredDocuments = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<ArrayList<IUserDocument>> userDocuments = new MutableLiveData<>(new ArrayList<>());

    List<DocumentDisplay> documentDisplays = new ArrayList<>();
    DocumentDisplay selectedDocumentDisplay;

    public DocumentDisplay getSelectedDocument() {
        return selectedDocumentDisplay;
    }

    public void setSelectedDocument(DocumentDisplay selectedDocumentDisplay) {
        this.selectedDocumentDisplay = selectedDocumentDisplay;
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
        this.requiredDocuments.setValue(requiredDocuments);
    }

    public void setUserDocuments(ArrayList<IUserDocument> userDocuments) {
        this.userDocuments.setValue(userDocuments);
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
}
