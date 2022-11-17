package com.example.doggo_android.Models;

public class Croquette {
    private String nom;
    private String description;
    private int prix;
    private int nbPanier;

    public Croquette(String nom, String description, int prix, int nbPanier) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.nbPanier = nbPanier;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getNbPanier() {
        return nbPanier;
    }

    public void setNbPanier(int nbPanier) {
        this.nbPanier = nbPanier;
    }
}
