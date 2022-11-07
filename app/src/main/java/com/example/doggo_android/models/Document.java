package com.example.doggo_android.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.doggo_android.enums.DOC_STATUS;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Document {
    private String name,description,rejectionReason;
    private DOC_STATUS status;
    private File documentMember;

    public Document(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = DOC_STATUS.NOT_SENT;
        this.rejectionReason = "";
    }

    public Document(String name, String description, DOC_STATUS status) {
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

    public File getDocumentMember() {
        return documentMember;
    }

    public void setDocumentMember(File documentMember) {
        this.documentMember = documentMember;
    }

}
