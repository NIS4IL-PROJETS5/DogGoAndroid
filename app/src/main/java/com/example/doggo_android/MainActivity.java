package com.example.doggo_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.example.doggo_android.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        assert navHostFragment != null;
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView,controller);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    controller.navigate(R.id.homeFragment);
                    break;
                case R.id.membersFragment:
                    controller.navigate(R.id.membersFragment);
                    break;
                case R.id.profileFragment:
                    controller.navigate(R.id.profileFragment);
                    break;
                case R.id.contactFragment:
                    controller.navigate(R.id.contactFragment);
                    break;
                case R.id.settingsFragment:
                    controller.navigate(R.id.settingsFragment);
                    break;
            }
            return true;
        });
    }
}