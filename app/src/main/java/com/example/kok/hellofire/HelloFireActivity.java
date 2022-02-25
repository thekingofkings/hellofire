package com.example.kok.hellofire;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Build the main page of the app.
 *
 * It populates layout {@link R.layout.activity_hello_fire} with a {@link PagerAdapter}.
 *
 * When this main activity starts, two tasks are completed in the background:
 *  1. Register a notification channel.
 *  2. Initiate a Firebase instance.
 */
public class HelloFireActivity extends AppCompatActivity {

    private static String CHANNEL_ID = "WHJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_fire);
        //createNotificationChannel();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("whj", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("whj", msg);
                        Toast.makeText(HelloFireActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        // set up the Tab Layout
        String[] tabTexts = {"Info", "Camera", "Log"};
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // set up the ViewPager
        final ViewPager2 viewPager = (ViewPager2) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle(), tabTexts.length);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTexts[position]);
            viewPager.setCurrentItem(position, true);
        }).attach();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
