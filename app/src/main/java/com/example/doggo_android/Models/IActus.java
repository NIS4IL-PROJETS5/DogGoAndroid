package com.example.doggo_android.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class IActus {

    @SerializedName("actId")
    private int id;
    @SerializedName("actDateDebut")
    private String startDate;
    @SerializedName("actDateFin")
    private String endDate;
    @SerializedName("actTitre")
    private String title;
    @SerializedName("actTexte")
    private String description;
    @SerializedName("actCachee")
    private boolean hidden;
    @SerializedName("actDateCachee")
    private String hiddenDate;
    @SerializedName("actDesactive")
    private boolean disabled;
    @SerializedName("actType")
    private int type; // 1=alerte, 2=simple, 3=future, 4=agility
    private List<IActuImage> images;

    public int getId() {return id;}
    public String getStartDate() {
        String[] date = startDate.split("-");
        return date[2] + "/" + date[1] + "/" + date[0];
}
    public String getEndDate() {return endDate;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public boolean isHidden() {return hidden;}
    public String getHiddenDate() {return hiddenDate;}
    public boolean isDisabled() {return disabled;}
    public int getType() {return type;}
    public List<IActuImage> getImages() {return images;}
    public void setImages(List<IActuImage> images) {this.images = images;}
}
