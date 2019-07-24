package com.example.kok.hellofire;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class HelloFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived (RemoteMessage message) {
        Map<String, String> msgs = message.getData();
        for (Map.Entry<String, String> entry : msgs.entrySet()) {
            Log.d("whj" + entry.getKey(), entry.getValue());
        }
    }
}
