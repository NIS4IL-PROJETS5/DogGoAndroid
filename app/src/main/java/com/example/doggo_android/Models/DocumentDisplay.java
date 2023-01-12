package com.example.doggo_android.Models;

import android.graphics.Bitmap;

import com.example.doggo_android.Enums.DOC_STATUS;

import java.util.HashMap;
import java.util.List;

public class DocumentDisplay {
    private String id;
    private String name;
    private String description;
    private String rejectionReason;
    private DOC_STATUS status;
    private HashMap<String, Bitmap> documentUrl = new HashMap<>();



    public DocumentDisplay(String id,String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = DOC_STATUS.NOT_SENT;
        this.rejectionReason = "";
    }

    public DocumentDisplay(String id,String name, String description, DOC_STATUS status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DOC_STATUS getStatus() {
        return status;
    }

    public void setStatus(DOC_STATUS status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String,Bitmap> getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(HashMap<String,Bitmap> documentUrl) {
        this.documentUrl = documentUrl;
    }
}
