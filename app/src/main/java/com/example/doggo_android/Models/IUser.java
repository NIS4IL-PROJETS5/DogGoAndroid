package com.example.doggo_android.Models;

import androidx.annotation.Nullable;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.google.gson.annotations.SerializedName;

public class IUser {

    @SerializedName(value="userId", alternate = "_id")
    private String id;
    private String token;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String role;
    private DOC_STATUS doc_status;

    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setEmail(String email) {this.email = email;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setRole(String role) {this.role = role;}
    public void setDoc_status(DOC_STATUS doc_status) {this.doc_status = doc_status;}

    public String getId() {
        return id;
    }
    public String getToken() {
        return token;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {return surname;}
    public String getEmail() {
        return email;
    }
    public String getPhone() {return phone;}
    public String getRole() {
        return role;
    }
    public DOC_STATUS getDoc_status() {return doc_status;}

    @Override
    public String toString() {
        return "IUser{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null && obj.getClass() != IUser.class) {
            return false;
        }
        return ((IUser) obj).getId().equals(this.getId());
    }
}
