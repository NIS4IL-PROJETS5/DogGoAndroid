package com.example.doggodocs.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggodocs.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentSharedViewModel extends ViewModel {

    List<Document> documentList = new ArrayList<>();

    private MutableLiveData<Document> selected = new MutableLiveData<>();

    public void setSelected(Document document){
        selected.setValue(document);
    }
    public LiveData<Document> getSelected(){
        return selected;
    }

    public  List<Document> getDocumentList(){
        if(this.documentList.size()<=0) initDocs();
        return documentList;
    }

    private void initDocs(){
        documentList.add(new Document("Document d'inscription 1","Document chien 1","Béatricre Mat",false,"https://demo.codeseasy.com/downloads/CodesEasy.pdf","https://demo.codeseasy.com/downloads/CodesEasy.pdf"));
        documentList.add(new Document("Document d'inscription 2","Document chien 2","Bob Poiz",true,"https://demo.codeseasy.com/downloads/CodesEasy.pdf","https://demo.codeseasy.com/downloads/CodesEasy.pdf"));
        documentList.add(new Document("Document d'inscription 3","Document chien 3","Jade Gyu",true,"https://demo.codeseasy.com/downloads/CodesEasy.pdf","https://demo.codeseasy.com/downloads/CodesEasy.pdf"));
        documentList.add(new Document("Document d'inscription 4","Document chien 4","Anna Fusde",false,"https://demo.codeseasy.com/downloads/CodesEasy.pdf","https://demo.codeseasy.com/downloads/CodesEasy.pdf"));
    }
}
