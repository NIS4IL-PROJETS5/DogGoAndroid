package com.example.doggo_android.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Models.IActus;

import java.util.ArrayList;
import java.util.List;

public class ActualitesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<IActus>> actus;

    public LiveData<ArrayList<IActus>> getActus() {
        if(actus == null){
            actus = new MutableLiveData<>(new ArrayList<>());
        }
        return actus;
    }

    public void setActus(ArrayList<IActus> actus) {
        this.actus.setValue(actus);
    }

    public void addActus(ArrayList<IActus> actus) {
        ArrayList<IActus> newActus = this.actus.getValue();
        newActus.addAll(actus);
        this.actus.setValue(newActus);
    }

    public void addOneActu(IActus actu) {
        ArrayList<IActus> newActus = this.actus.getValue();
        newActus.add(actu);
        this.actus.setValue(newActus);
    }
}
