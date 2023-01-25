package com.example.doggo_android.Enums;

import com.google.gson.annotations.SerializedName;

public enum DOC_STATUS {
    @SerializedName("0")
    NOT_SENT("Ajouter Document"),
    @SerializedName("pending")
    PENDING("Document en attente"),
    @SerializedName("rejected")
    REJECTED("Ajouter Document"),
    @SerializedName("approved")
    ACCEPTED("Document accepté");

    public final String StatusMessage;

    DOC_STATUS(String statusMessage) {
        StatusMessage = statusMessage;
    }
}
