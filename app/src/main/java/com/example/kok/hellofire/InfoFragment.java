package com.example.kok.hellofire;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;


public class InfoFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.kok.hellofire.MESSAGE";
    private static String CHANNEL_ID = "WHJ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        Button sendButton = v.findViewById(R.id.button_display_info);
        sendButton.setOnClickListener(this);

        Button timeButton = v.findViewById(R.id.button_time);
        timeButton.setOnClickListener(this);
        return v;
    }

    private void setNotification(String msg) {
        Intent intent = new Intent(getActivity(), HelloFireActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("To HJ")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        int notificationID = (int) (new Date().getTime() / 1000); // unix timestamp in seconds
        notificationManager.notify(notificationID, builder.build());
    }


    public void showCurrentTime(View view) {
        Intent intent = new Intent(getActivity(), ShowInfoActivity.class);
        Date c = new Date();
        String msg = c.toString();
        intent.putExtra(EXTRA_MESSAGE, msg);
        Log.d("whj", msg);
        setNotification(msg);
        startActivity(intent);
    }

    public void displayInfo(View view) {
        Intent intent = new Intent(getActivity(), ShowInfoActivity.class);
        EditText et = getView().findViewById(R.id.text_input);
        String msg = et.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, msg);
        Log.d("whj", msg);
        setNotification(msg);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_display_info:
                displayInfo(view);
                break;
            case R.id.button_time:
                showCurrentTime(view);
                break;
            default:
                break;
        }
    }
}
