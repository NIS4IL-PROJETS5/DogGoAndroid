package com.example.doggo_android.Models;


import com.example.doggo_android.Enums.DOC_STATUS;
import com.google.gson.annotations.SerializedName;

public class IRequiredDocument {
    @SerializedName(value="docId", alternate = {"_id"})
    private String reqDocId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("reqDocUrl")
    private String url;
    @SerializedName("status")
    private DOC_STATUS status;
    @SerializedName("rejectionReason")
    private String rejectionReason;

    public String getReqDocId() {
        return reqDocId;
    }

    public void setReqDocId(String reqDocId) {
        this.reqDocId = reqDocId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
