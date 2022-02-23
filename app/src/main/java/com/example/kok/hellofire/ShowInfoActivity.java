package com.example.kok.hellofire;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Activity to populate the layout {@link R.layout.activity_show_info}.
 *
 * It has a textview area to show a message.
 */
public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        Intent intent = getIntent();
        String info = intent.getStringExtra(InfoFragment.EXTRA_MESSAGE);
        Log.d("msg", info);
        TextView tv = new TextView(this);
        tv.setTextSize(24);
        if (info.isEmpty())
            tv.setText("No message to show.");
        else
            tv.setText(info);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_show_info);
        layout.addView(tv);
    }
}
