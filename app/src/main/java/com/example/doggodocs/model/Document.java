package com.example.doggodocs.model;

public class Document {

    private String documentInscription;
    private String documentChien;
    private String auteur;
    private String lienInscription;
    private String lienChien;
    private boolean statut;

    public Document(String documentInscription, String documentChien, String auteur, boolean statut, String lienInscription, String lienChien) {
        this.documentInscription = documentInscription;
        this.documentChien = documentChien;
        this.auteur = auteur;
        this.statut = statut;
        this.lienInscription = lienInscription;
        this.lienChien = lienChien;
    }

    public String getDocumentInscription() {
        return documentInscription;
    }

    public void setDocumentInscription(String documentInscription) {
        this.documentInscription = documentInscription;
    }

    public String getDocumentChien() {
        return documentChien;
    }

    public void setDocumentChien(String documentChien) {
        this.documentChien = documentChien;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getLienInscription() {
        return lienInscription;
    }

    public void setLienInscription(String lienInscription) {
        this.lienInscription = lienInscription;
    }

    public String getLienChien() {
        return lienChien;
    }

    public void setLienChien(String lienChien) {
        this.lienChien = lienChien;
    }
}
