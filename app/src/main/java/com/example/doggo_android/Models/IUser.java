package com.example.doggo_android.Models;

import com.google.gson.annotations.SerializedName;

public class IUser {

    @SerializedName("userId")
    private String id;
    private String token;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String role;

    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setEmail(String email) {this.email = email;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setRole(String role) {this.role = role;}

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
}
