package com.example.doggo_android.ViewModels;

import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Models.Panier;

import java.util.ArrayList;
import java.util.List;

public class PanierSharedViewModel extends ViewModel {
    private List<Panier> paniers = new ArrayList<>();

    public int addCroquetteToPanier(Panier panier){
        paniers.add(panier);
        return paniers.size()-1;
    }
    public  List<Panier> getCroquettePanier(){
        return paniers;
    }
}
