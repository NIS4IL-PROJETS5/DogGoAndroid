package com.example.doggo_android.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class IActus {

    @SerializedName("actId")
    private int id;
    @SerializedName("actDateDebut")
    private Date startDate;
    @SerializedName("actDateFin")
    private Date endDate;
    @SerializedName("actTitre")
    private String title;
    @SerializedName("actTexte")
    private String description;
    @SerializedName("actCachee")
    private boolean hidden;
    @SerializedName("actDateCachee")
    private Date hiddenDate;
    @SerializedName("actDesactive")
    private boolean disabled;
    @SerializedName("actType")
    private int type; // 1=alerte, 2=simple, 3=future, 4=agility

    public int getId() {return id;}
    public Date getStartDate() {return startDate;}
    public Date getEndDate() {return endDate;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public boolean isHidden() {return hidden;}
    public Date getHiddenDate() {return hiddenDate;}
    public boolean isDisabled() {return disabled;}
    public int getType() {return type;}
}
