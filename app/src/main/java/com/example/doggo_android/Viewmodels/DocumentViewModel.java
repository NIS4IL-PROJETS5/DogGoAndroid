package com.example.doggo_android.ViewModels;

import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.Document;

import java.util.ArrayList;
import java.util.List;

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

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void initDocuments(){
        documents.add(new Document("Document 1","Description 1"));
        documents.add(new Document("Document 2","Description 2", DOC_STATUS.PENDING));
        documents.add(new Document("Document 3","Description 3",DOC_STATUS.ACCEPTED));
        documents.add(new Document("Document 4","Description 4",DOC_STATUS.REJECTED));
    }


}
