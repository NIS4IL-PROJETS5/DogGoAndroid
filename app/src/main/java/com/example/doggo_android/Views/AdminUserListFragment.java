package com.example.doggo_android.Views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doggo_android.Adapters.UserListAdapter;
import com.example.doggo_android.R;
import com.example.doggo_android.ViewModels.UserViewModel;
import com.example.doggo_android.databinding.FragmentAdminUserListBinding;

public class AdminUserListFragment extends Fragment {

    FragmentAdminUserListBinding binding;

    UserViewModel viewModel;

    public AdminUserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentAdminUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        RecyclerView rv = binding.UserListRecyclerView;
        final UserListAdapter adapter = new UserListAdapter(new UserListAdapter.UserDiffCallback(), user -> {
            viewModel.setSelectedUser(user);
            Log.d("AdminUserListFragment", "onViewCreated: " + viewModel.getSelectedUser());
            Navigation.findNavController(view).navigate(R.id.action_adminUserListFragment_to_adminUserDocumentListFragment);
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getUsers(requireContext()).observe(getViewLifecycleOwner(), data -> {
            adapter.submitList(data);
            adapter.notifyDataSetChanged();
            Log.d("AdminUserList", "onViewCreated: update list");
        });

    }
}