package com.example.doggo_android.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Models.IActus;

import java.util.ArrayList;
import java.util.List;

public class ActualitesViewModel extends ViewModel {

    private ArrayList<IActus> actus = new ArrayList<>();

    public MutableLiveData<ArrayList<IActus>> getActus() {
        MutableLiveData<ArrayList<IActus>> data = new MutableLiveData<>();
        data.setValue(actus);
        return data;
    }
}
