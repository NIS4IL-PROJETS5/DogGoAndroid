package com.example.doggo_android.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("userId")
    private String id;

    private String token;

    private String name;

    private String email;

    private String role;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
