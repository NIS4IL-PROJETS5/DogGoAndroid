package com.example.doggo_android.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doggo_android.Adapters.ActualitesAdapter;
import com.example.doggo_android.Models.IActus;
import com.example.doggo_android.Models.RetrofitRequests;
import com.example.doggo_android.Utils;
import com.example.doggo_android.ViewModels.ActualitesViewModel;
import com.example.doggo_android.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<IActus> actus;
    ActualitesAdapter adapter;
    Integer page = 1;
    RetrofitRequests requests;
    String token;
    ActualitesViewModel viewModel = new ActualitesViewModel();
    Integer type = 2;
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

        this.requests = Utils.getRetrofitCon(requireContext());
        this.token = Utils.getToken(requireContext());
        this.handleGetActualites();

        binding.fragmentActualitesFilterLogo.setOnClickListener(view1 -> {
            String[] options = {"Alerte", "Tout", "Agility"};

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Afficher les actualités de type :")
                    .setItems(options, (dialog, which) -> {
                        type = options[which] == "Agility" ? 4 :  which + 1;
                        viewModel.setActus(new ArrayList<IActus>());
                        page = 1;
                        resetAdapter();
                        handleGetActualites();
                    });
            builder.create().show();
        });

        binding.fragmentRvActualitesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    page++;
                    handleGetActualites();
                }
            }
        });
    }


    public void resetAdapter() {
        adapter = new ActualitesAdapter(requireContext(), viewModel.getActus().getValue());

        int lastIndex = adapter.getItemCount() - 1;
        RecyclerView recyclerView = binding.fragmentRvActualitesRv;
        recyclerView.scrollToPosition(lastIndex);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        binding.fragmentRvActualitesRv.setLayoutManager(manager);
        binding.fragmentRvActualitesRv.setAdapter(adapter);
    }

    public void handleGetActualites() {
        Call<ArrayList<IActus>> call = requests.executeGetActus("Bearer " + token, String.valueOf(type) ,String.valueOf(page));

        call.enqueue(new Callback<ArrayList<IActus>>() {
            @Override
            public void onResponse(Call<ArrayList<IActus>> call, Response<ArrayList<IActus>> response) {
                if (response.isSuccessful()) {

                    actus = response.body();

                    if(viewModel.getActus() == null) {
                        viewModel.setActus(actus);
                    } else {
                        viewModel.addActus(actus);
                    }

                    resetAdapter();
                    binding.fragmentActualitesRvTv.setVisibility(View.GONE);
                } else {
                    Log.d("TAG", "onResponse: " + response.code());
                    binding.fragmentActualitesRvTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IActus>> call, Throwable t) {
                Log.d("HomeFragment", "onFailure: " + t.getMessage());
                Toast.makeText(requireContext(), "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}