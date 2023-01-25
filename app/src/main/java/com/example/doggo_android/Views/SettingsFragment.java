package com.example.doggo_android.Views;
import com.example.doggo_android.FirebaseMessagingHandler;
import com.example.doggo_android.R;
import com.example.doggo_android.Utils;
import com.example.doggo_android.databinding.FragmentSettingsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class SettingsFragment extends Fragment {

    private static final String CHANNEL_ID = "doggo_channel_id";
    FragmentSettingsBinding binding;
    NotificationCompat.Builder builder;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         // This is creating a notification builder.
         this.builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Doggo Notification")
                .setContentText("This is the subject of notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("This is more details about this notification"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        binding.show.setOnClickListener(v ->
        {
            createNotificationChannel();
            getContext();
            NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        });

        binding.turnOnOffNotification.setOnClickListener(v ->
        {
            Log.d("TESTBUTTON", "onViewCreated: TEST ");

            //Request for notification permission
            //Not working to check if permission is granted or not
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                    }, 100);
                    Log.d("TESTBUTTON", "onViewCreated: Version supported ");
                } else {
                    Log.d("TESTBUTTON", "onViewCreated: Version not supported");
                }
            }
        });

        binding.adminUserPanelButton.setOnClickListener(v -> {
            if (Utils.getRole(requireContext()).equals("admin")){
                Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_adminMainPanelFragment);
            } else {
                Toast.makeText(requireContext(), "Vous ne disposez pas des droits nécessaires", Toast.LENGTH_SHORT).show();
            }

        });


        binding.switchNotificationAlert.setChecked(sharedPreferences.getBoolean("switchNotificationAlerte", false));
        binding.switchNotificationSimple.setChecked(sharedPreferences.getBoolean("switchNotificationSimple", false));
        binding.switchNotificationFuture.setChecked(sharedPreferences.getBoolean("switchNotificationFuture", false));
        binding.switchNotificationAgility.setChecked(sharedPreferences.getBoolean("switchNotificationAgility", false));

        binding.switchNotificationAlert.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("switchNotificationAlerte", isChecked);
            editor.apply();
            if (isChecked){
                FirebaseMessagingHandler.subscribeToTopic("alerte", requireContext(),true);
            } else {
                FirebaseMessagingHandler.unsubscribeFromTopic("alerte", requireContext());
            }
        });

        binding.switchNotificationSimple.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("switchNotificationSimple", isChecked);
            editor.apply();
            if (isChecked){
                FirebaseMessagingHandler.subscribeToTopic("simple", requireContext(),true);
            } else {
                FirebaseMessagingHandler.unsubscribeFromTopic("simple", requireContext());
            }
        });

        binding.switchNotificationFuture.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("switchNotificationFuture", isChecked);
            editor.apply();
            if (isChecked){
                FirebaseMessagingHandler.subscribeToTopic("future", requireContext(),true);
            } else {
                FirebaseMessagingHandler.unsubscribeFromTopic("future", requireContext());
            }
        });

        binding.switchNotificationAgility.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("switchNotificationAgility", isChecked);
            editor.apply();
            if (isChecked){
                FirebaseMessagingHandler.subscribeToTopic("agility", requireContext(),true);
            } else {
                FirebaseMessagingHandler.unsubscribeFromTopic("agility", requireContext());
            }
        });

    }

        /**
         * Create a notification channel with the ID "DoggoChannel" and the name "DoggoChannel" and the
         * description "Channel for Doggo" and the importance NotificationManager.IMPORTANCE_DEFAULT
         */
        private void createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "DoggoChannel";
                String description = "Channel for Doggo";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }
}