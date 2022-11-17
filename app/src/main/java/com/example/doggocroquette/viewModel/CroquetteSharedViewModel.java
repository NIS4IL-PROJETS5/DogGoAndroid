package com.example.doggocroquette.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggocroquette.model.Croquette;

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
        croquettes.add(new Croquette("Iams Vitality - poulet","pour Chiens Seniors Grande Race. Aliment 100 % complet et équilibré. Sans épaississant. Sans OGM . Sans colorants . Sans blé",40,0));
        croquettes.add(new Croquette("Iams Vitality - allégées", "pour chiens Adultes Petite et Moyenne Race. Aliment 100 % complet et équilibré. Sans épaississant. Sans OGM . Sans colorants . Sans blé",40,0));
    }
}
