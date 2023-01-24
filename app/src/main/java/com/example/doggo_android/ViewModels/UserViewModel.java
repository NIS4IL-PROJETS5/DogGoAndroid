package com.example.doggo_android.ViewModels;

import android.app.Application;
import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doggo_android.Enums.DOC_STATUS;
import com.example.doggo_android.Models.IDOC_STATUSWrapper;
import com.example.doggo_android.Models.IUser;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserViewModel extends AndroidViewModel {
    MutableLiveData<List<IUser>> users = new MutableLiveData<>();
    MutableLiveData<IUser> selectedUser = new MutableLiveData<>();
    RetrofitRequests requests;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<IUser>> getUsers(Context context) {
        if (users.getValue() == null){
            users.setValue(new ArrayList<>());
        }
        if (users.getValue().isEmpty()) {
            retrieveUsers(context);
        }
        return users;
    }

    public void setUsers(List<IUser> users) {
        this.users.postValue(users);
    }

    public IUser getSelectedUser() {
        return selectedUser.getValue();
    }

    public void setSelectedUser(IUser selectedUser) {
        this.selectedUser.setValue(selectedUser);
    }

    private void retrieveUsers(Context context) {
        requests = Utils.getRetrofitCon(context);
        Call<List<IUser>> call = requests.executeGetUsers(Utils.getToken(context));

        call.enqueue(new Callback<List<IUser>>() {
            @Override
            public void onResponse(Call<List<IUser>> call, Response<List<IUser>> response) {
                if (response.isSuccessful()) {
                    setUsers(response.body());

                    if (getUsers(context).getValue().size() > 0) {
                        for (IUser user : getUsers(context).getValue()) {
                            retrieveUsersDocStatus(context, user);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<IUser>> call, Throwable t) {
                Toast.makeText(context, "Un problème a eu lieu lors de la récupération des utilisateurs", Toast.LENGTH_LONG).show();
                Log.d("UserListAdmin", "onFailure: " + t.getMessage());
            }
        });
    }

    private void retrieveUsersDocStatus(Context context, IUser user){
        requests = Utils.getRetrofitCon(context);
        Call<IDOC_STATUSWrapper> call = requests.executeGetDocStatus(user.getId(),Utils.getToken(context));

        call.enqueue(new Callback<IDOC_STATUSWrapper>() {
            @Override
            public void onResponse(Call<IDOC_STATUSWrapper> call, Response<IDOC_STATUSWrapper> response) {
                if (response.isSuccessful()) {
                    DOC_STATUS docStatus = response.body().doc_status;
                    user.setDoc_status(docStatus);
                    getUsers(context).getValue().set(getUsers(context).getValue().indexOf(user), user);
                    setUsers(getUsers(context).getValue());
                }
                else {
                    Log.d("User", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IDOC_STATUSWrapper> call, Throwable t) {
                Toast.makeText(context, "Un problème a eu lieu lors de la récupération des utilisateurs", Toast.LENGTH_LONG).show();
                Log.d("UserListAdmin", "onFailure: " + t.getMessage() + " call:" + call.request().url());
                Log.d("User", "onResponse: " + user.getId() + " " + user.getDoc_status());
            }
        });
    }
}
