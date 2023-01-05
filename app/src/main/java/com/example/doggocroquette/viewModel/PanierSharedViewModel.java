package com.example.doggocroquette.viewModel;

import static java.security.AccessController.getContext;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.doggocroquette.model.Croquette;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

public class PanierSharedViewModel extends ViewModel {
    private List<Croquette> croquettespanier = new ArrayList<Croquette>();

    private int total = 0;

    public int getTotal() {
        return total;
    }


    public int addCroquetteToPanier(Croquette croquette){
        if(croquettespanier.isEmpty()){
            croquettespanier.add(croquette);
            croquette.setNbPanier(1);
            total = total+ croquette.getPrix();
        }else {
            for (Croquette c : croquettespanier) {
                if (c.getNom().equals(croquette.getNom()) && c.getDescription().equals(croquette.getDescription())) {
                    c.setNbPanier(c.getNbPanier() + 1);
                    total = total + croquette.getPrix();
                    return 0;
                }
            }
            croquettespanier.add(croquette);
            total = total + croquette.getPrix();
        }
        return croquettespanier.size()-1;
    }


    public int removeCroquetteFromPanier(Croquette croquette) {
        int index = croquettespanier.indexOf(croquette);
        if (index != -1) {
            Croquette croquetteInPanier = croquettespanier.get(index);
            int nbPanier = croquetteInPanier.getNbPanier();
            if (nbPanier > 1) {
                // Si le nbPanier est supérieur à 1, on décrémente simplement le nbPanier
                croquetteInPanier.setNbPanier(nbPanier - 1);
            } else {
                // Si le nbPanier est égal à 1, on retire la croquette du panier
                croquettespanier.remove(index);
            }
            total = total - croquette.getPrix();
        }
        return croquettespanier.size()-1;
    }
    public  List<Croquette> getCroquettePanier(){
        return croquettespanier;
    }
}