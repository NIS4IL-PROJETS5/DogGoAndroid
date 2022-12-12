package com.example.doggo_android.ViewModels;

import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentViewModel extends ViewModel {

    List<Document> documents = new ArrayList<>();
    Document selectedDocument;

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public List<Document> getDocuments() {
        if (documents.size() == 0){
            initDocuments();
        }
        return documents;
    }

    public List<Document> getPendingDocuments(){
        List<Document> documents = new ArrayList<>();
        for (Document document : this.documents){
            if (document.getStatus() == DOC_STATUS.NOT_SENT || document.getStatus() == DOC_STATUS.REJECTED){
                documents.add(document);
            }
        }
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void initDocuments(){
        documents.add(new Document("Certificat de dressage","Certificat de dressage de votre chien"));
        documents.add(new Document("Carnet de vaccination","Carnet de vaccination de votre chien", DOC_STATUS.PENDING));
        documents.add(new Document("Attestation de responsabilité civile","Votre attestation de responsabilité civile",DOC_STATUS.ACCEPTED));
        Document falseDoc = new Document("Carte d'identification","Votre carte d'identification a la charte canine",DOC_STATUS.REJECTED);
        falseDoc.setRejectionReason("Document incomplet");
        documents.add(falseDoc);
    }


}
