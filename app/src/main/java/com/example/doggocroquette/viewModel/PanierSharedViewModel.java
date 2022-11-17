package com.example.doggocroquette.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.doggocroquette.model.Panier;

import java.util.ArrayList;
import java.util.List;

public class PanierSharedViewModel extends ViewModel {
    private List<Panier> paniers = new ArrayList<Panier>();

    public int addCroquetteToPanier(Panier panier){
        paniers.add(panier);
        return paniers.size()-1;
    }
    public  List<Panier> getCroquettePanier(){
        return paniers;
    }
}