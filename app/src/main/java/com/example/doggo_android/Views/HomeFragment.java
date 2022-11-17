package com.example.doggo_android.Views;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggo_android.Adapters.ActualitesAdapter;
import com.example.doggo_android.Models.IActus;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentHomeBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    RetrofitRequests requests;
    ArrayList<IActus> actus;
    ActualitesAdapter adapter;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("HomeFragment", "START");

        String apiUrl = Utils.getConfigValue(requireContext(), "api_url");
//
//        try {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create()).build();
//            requests = retrofit.create(RetrofitRequests.class);
//        } catch (Exception e) {
//            Log.e("HomeFragment", "onViewCreated: " + e.getMessage());
//        }

        Log.d("HomeFragment", "AVANT CALL");
        this.handleGetActualites(apiUrl);
    }


    public void handleGetActualites(String apiUrl) {

//        SharedPreferences preferences = requireActivity().getSharedPreferences("DogGo", 0);
//        String token = preferences.getString("token", null);
//        Log.d("HomeFragment", "handleGetActualites: " + token);
//        Call<IActus> call = requests.executeGetActus("Bearer " + token, "1");
//
//        call.enqueue(new Callback<IActus>() {
//            @Override
//            public void onResponse(Call<IActus> call, Response<IActus> response) {
//                switch (response.code()) {
//                    case 200:
//                        IActus actus = response.body();
//                        Log.d("HomeFragment", "onResponse" + actus);
//                        break;
//
//                    default:
//                        Log.e("HomeFragment", "onResponse: " + response.code());
//                        break;
//                }
//            }
//            @Override
//            public void onFailure(Call<IActus> call, Throwable t) {
//                Log.e("HomeFragment", "onFailure: " + t.getMessage());
//            }
//        });

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                // on below line we are calling add
                // Converter factory as Gson converter factory.
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitRequests requests = retrofit.create(RetrofitRequests.class);

        // on below line we are calling a method to get all the courses from API.
        SharedPreferences preferences = requireActivity().getSharedPreferences("DogGo", 0);
        String token = preferences.getString("token", null);
        Log.d("HomeFragment", "handleGetActualites: " + token);
        Call<ArrayList<IActus>> call = requests.executeGetActus("Bearer " + token, "1");

        // on below line we are calling method to enqueue and calling
        // all the data from array list.
        call.enqueue(new Callback<ArrayList<IActus>>() {
            @Override
            public void onResponse(Call<ArrayList<IActus>> call, Response<ArrayList<IActus>> response) {
                // inside on response method we are checking
                // if the response is success or not.
                Log.d("HomeFragment", "onResponse: " + response.code());
                if (response.isSuccessful()) {


                    // below line is to add our data from api to our array list.
                    actus = response.body();
                    Log.d("HomeFragment", "onResponse: " + actus);

                    // below line we are running a loop to add data to our adapter class.
                    for (int i = 0; i < actus.size(); i++) {
                        adapter = new ActualitesAdapter(requireContext(), actus);

                        // below line is to set layout manager for our recycler view.
                        LinearLayoutManager manager = new LinearLayoutManager(requireContext());

                        // setting layout manager for our recycler view.
                        binding.fragmentRvActualitesRv.setLayoutManager(manager);

                        // below line is to set adapter to our recycler view.
                        binding.fragmentRvActualitesRv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IActus>> call, Throwable t) {
                // in the method of on failure we are displaying a
                // toast message for fail to get data.
                Log.d("HomeFragment", "onFailure: " + t.getMessage());
                Toast.makeText(requireContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}