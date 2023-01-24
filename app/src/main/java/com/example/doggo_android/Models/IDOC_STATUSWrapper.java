package com.example.doggo_android.Models;

import com.example.doggo_android.Enums.DOC_STATUS;

public class IDOC_STATUSWrapper {
    public DOC_STATUS doc_status;

    public IDOC_STATUSWrapper(DOC_STATUS doc_status) {
        this.doc_status = doc_status;
    }

    public DOC_STATUS getDoc_status() {
        return doc_status;
    }

    public void setDoc_status(DOC_STATUS doc_status) {
        this.doc_status = doc_status;
    }
}
