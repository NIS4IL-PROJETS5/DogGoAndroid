package com.example.doggo_android.ViewModels;

import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Models.IUser;

public class ConnectionViewModel extends ViewModel {

    private IUser user;

    public IUser getUser() {
        if(user == null){
            user = new IUser();
        }
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }
}
