package com.example.kok.hellofire;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class HelloFireActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.kok.hellofire.MESSAGE";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_fire);
    }

    public void showCurrentTime(View view) {
        Intent intent = new Intent(this, ShowInfoActivity.class);
        Date c = new Date();
        String msg = c.toString();
        intent.putExtra(EXTRA_MESSAGE, msg);
        Log.d("whj", msg);
        startActivity(intent);
    }

    public void displayInfo(View view) {
        Intent intent = new Intent(this, ShowInfoActivity.class);
        EditText et = (EditText) findViewById(R.id.text_input);
        String msg = et.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, msg);
        Log.d("whj", msg);
        startActivity(intent);
    }

    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
