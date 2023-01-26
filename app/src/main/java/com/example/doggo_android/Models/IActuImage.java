package com.example.doggo_android.Models;

import android.content.Context;

import com.example.doggo_android.Utils;

public class IActuImage {
    private int actphoId;
    private int actId;
    private String actphoFichier;
    private String actphoCommentaire;

    public int getActphoId() {return actphoId;}
    public int getActId() {return actId;}
    public String getActphoFichier() {return actphoFichier;}
    public String getActphoFichierUrl(Context context) {
        String apiUrl = Utils.getConfigValue(context, "api_url");
        return apiUrl + "images/" + actphoFichier;
    }
    public String getActphoCommentaire() {return actphoCommentaire;}

    public void setActphoId(int actphoId) {this.actphoId = actphoId;}
    public void setActId(int actId) {this.actId = actId;}
    public void setActphoFichier(String actphoFichier) {this.actphoFichier = actphoFichier;}
    public void setActphoCommentaire(String actphoCommentaire) {this.actphoCommentaire = actphoCommentaire;}
}
