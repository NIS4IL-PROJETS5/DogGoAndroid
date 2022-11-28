package com.example.doggocroquette.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.doggocroquette.model.Croquette;

import java.util.ArrayList;
import java.util.List;

public class PanierSharedViewModel extends ViewModel {
    private List<Croquette> croquettes = new ArrayList<Croquette>();

    public int addCroquetteToPanier(Croquette croquette){
        croquettes.add(croquette);
        return croquettes.size()-1;
    }
    public  List<Croquette> getCroquettePanier(){
        return croquettes;
    }
}