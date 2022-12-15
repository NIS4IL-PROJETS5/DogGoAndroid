package com.example.doggo_android.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        this.requests = Utils.getRetrofitCon(requireContext());
//        this.token = Utils.getToken(requireContext());
//        this.handleGetActualites();
//
//
//        binding.fragmentActualitesFilterLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String[] options = {"Alerte", "Simple", "Future", "Agility", "Tout"};
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//                builder.setTitle("Afficher les actualités de type :")
//                        .setItems(options, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Triez la liste d'actualités en fonction de l'option sélectionnée
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.requests = Utils.getRetrofitCon(requireContext());
        this.token = Utils.getToken(requireContext());
        this.handleGetActualites();

        binding.fragmentActualitesFilterLogo.setOnClickListener(view1 -> {
            String[] options = {"Alerte", "Simple", "Future", "Agility", "Tout"};

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Afficher les actualités de type :")
                    .setItems(options, (dialog, which) -> {
                        // Récupérez la sélection de l'utilisateur
                        String selectedOption = options[which];

                        // Triez la liste d'actualités en fonction de l'option sélectionnée
                        

                        Log.d("TAG", "onClick: " + selectedOption);
                    });
            builder.create().show();
        });
    }



    /**
     * It gets the actualites from the API and displays them in a recycler view
     */
    public void handleGetActualites() {

        Call<ArrayList<IActus>> call = requests.executeGetActus("Bearer " + token, String.valueOf(page));

        List<IActus> itemsToRemove = new ArrayList<>();

        call.enqueue(new Callback<ArrayList<IActus>>() {
            @Override
            public void onResponse(Call<ArrayList<IActus>> call, Response<ArrayList<IActus>> response) {
                if (response.isSuccessful()) {

                    actus = response.body();


                    for (IActus actu : actus) {
                        if (actu.getType() == 4) {
                            itemsToRemove.add(actu);
                        } else {
                            actu.setDescription(Utils.truncate(actu.getDescription(), 100));
                        }
                    }
                    actus.removeAll(itemsToRemove);


                    if(viewModel.getActus() == null) {
                        viewModel.setActus(actus);
                    } else {
                        viewModel.addActus(actus);
                    }

                    // Triez la liste d'actualités en fonction du type
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(actus, Comparator.comparingInt(IActus::getType));
                    }

                    adapter = new ActualitesAdapter(requireContext(), viewModel.getActus().getValue());
                    LinearLayoutManager manager = new LinearLayoutManager(requireContext());
                    binding.fragmentRvActualitesRv.setLayoutManager(manager);
                    binding.fragmentRvActualitesRv.setAdapter(adapter);
                    binding.fragmentRvActualitesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (!recyclerView.canScrollVertically(1)) {
                                page++;
                                Toast.makeText(requireContext(), "Page " + page, Toast.LENGTH_SHORT).show();
                                handleGetActualites();
                            }
                        }
                    });
                    binding.fragmentActualitesRvTv.setVisibility(View.GONE);
                } else {
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