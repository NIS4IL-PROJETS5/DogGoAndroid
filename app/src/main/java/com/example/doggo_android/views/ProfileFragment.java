package com.example.doggo_android.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.doggo_android.R;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button button = view.findViewById(R.id.button_profile_modify2);
        button.setOnClickListener((View.OnClickListener) v -> {
            //naviagte to candidature form
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            assert navHostFragment != null;
            NavController controller = navHostFragment.getNavController();
            controller.navigate(R.id.action_profileFragment_to_candidatureFormFragment);



        });
        return view;

    }
}