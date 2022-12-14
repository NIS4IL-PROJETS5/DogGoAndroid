package com.example.doggo_android.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Models.Croquette;

import java.util.ArrayList;
import java.util.List;

public class CroquetteSharedViewModel extends ViewModel {

    private List<Croquette> croquettes = new ArrayList<Croquette>();

    private MutableLiveData<Croquette> selected = new MutableLiveData<>();


    public void setSelected(Croquette croquette){
        selected.setValue(croquette);
    }
    public LiveData<Croquette> getSelected(){
        return selected;
    }

    public  List<Croquette> getCroquettes(){
        if(this.croquettes.size()<=0) initVets();
        return croquettes;
    }

    private void initVets(){
        croquettes.add(new Croquette("Iams - poulet","pour Chiens Seniors Grande Race.",40,0));
        croquettes.add(new Croquette("Iams - allégées", "pour chiens Adultes Petite et Moyenne Race.",40,0));
    }
}
