package com.example.doggo_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.doggo_android.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        assert navHostFragment != null;
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView,controller);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.compteFragment:
                    controller.navigate(R.id.compteFragment2);
                    break;
                case R.id.homeFragment:
                    controller.navigate(R.id.homeFragment);
                    break;
                case R.id.contactFragment:
                    controller.navigate(R.id.contactFragment);
                    break;
                case R.id.settingsFragment:
                    controller.navigate(R.id.settingsFragment);
                    break;
                case R.id.membersFragment:
                    controller.navigate(R.id.membersFragment);
                    break;
            }
            return true;
        });


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        askNotificationPermission();


    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(task -> {
                                if (!task.isSuccessful()) {
                                    Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                String token = task.getResult();
                                Log.d("TAG", token);
                                setupNotification();
                            });
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        Log.d("Permission", "askNotificationPermission:");
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.d("Permission", "askNotificationPermission: Permission already granted");
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
                Log.d("Permission", "askNotificationPermission: Permission not granted, but should show rationale");
            } else {
                // Directly ask for the permission
                Log.d("Permission", "askNotificationPermission: Permission not granted, requesting");
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            // Permission is automatically granted on API < 33
            Log.d("Permission", "askNotificationPermission: Permission automatically granted");
            setupNotification();

        }
    }

    private void setupNotification(){
        if (!sharedPreferences.getBoolean("notificationInitialized", false)) {
            editor.putBoolean("notificationInitialized", true);
            editor.putBoolean("switchNotificationAlerte", true);
            editor.putBoolean("switchNotificationSimple",true);
            editor.putBoolean("switchNotificationFuture",false);
            editor.putBoolean("switchNotificationAgility",false);
            editor.apply();
        }

        if (sharedPreferences.getBoolean("switchNotificationAlerte", false)) {
            FirebaseMessagingHandler.subscribeToTopic("alerte",this,false);
        }

        if (sharedPreferences.getBoolean("switchNotificationSimple", false)) {
            FirebaseMessagingHandler.subscribeToTopic("simple",this,false);
        }

        if (sharedPreferences.getBoolean("switchNotificationFuture", false)) {
            FirebaseMessagingHandler.subscribeToTopic("future",this,false);
        }

        if (sharedPreferences.getBoolean("switchNotificationAgility", false)) {
            FirebaseMessagingHandler.subscribeToTopic("agility",this,false);
        }
    }
}