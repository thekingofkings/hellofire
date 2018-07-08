package com.example.kok.hellofire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        Intent intent = getIntent();
        String info = intent.getStringExtra(HelloFireActivity.EXTRA_MESSAGE);
        Log.d("msg", info);
        TextView tv = new TextView(this);
        tv.setTextSize(40);
        tv.setText(info);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_show_info);
        layout.addView(tv);
    }
}
