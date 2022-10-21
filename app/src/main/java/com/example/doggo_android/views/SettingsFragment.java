package com.example.doggo_android.views;
import com.example.doggo_android.R;
import com.example.doggo_android.databinding.FragmentSettingsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends Fragment {

    private static final String CHANNEL_ID = "doggo_channel_id";
    FragmentSettingsBinding binding;
    NotificationCompat.Builder builder;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
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




//        }
//    }