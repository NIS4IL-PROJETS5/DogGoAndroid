package com.example.doggo_android.Models;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IUserDocument {
    @SerializedName("docId")
    private String id;
    private IUser user;
    @SerializedName("userId")
    private String userId;
    @SerializedName("reqDoc")
    private IRequiredDocument requiredDocument;
    @SerializedName("docUrls")
    private List<String> documentUrl;
    @SerializedName("status")
    private DOC_STATUS status;
    @SerializedName("rejectionReason")
    private String rejectionReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public IRequiredDocument getRequiredDocument() {
        return requiredDocument;
    }

    public void setRequiredDocument(IRequiredDocument requiredDocument) {
        this.requiredDocument = requiredDocument;
    }

    public List<String> getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(List<String> documentUrl) {
        this.documentUrl = documentUrl;
    }

    public DOC_STATUS getStatus() {
        return status;
    }

    public void setStatus(DOC_STATUS status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
