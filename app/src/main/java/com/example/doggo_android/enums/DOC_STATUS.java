package com.example.doggo_android.enums;

public enum DOC_STATUS {
    NOT_SENT("Ajouter Document"),
    PENDING("Document en attente"),
    REJECTED("Ajouter Document"),
    ACCEPTED("Document accepté");

    public final String StatusMessage;

    DOC_STATUS(String statusMessage) {
        StatusMessage = statusMessage;
    }
}
