package com.example.kok.hellofire;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class HelloFireActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.kok.hellofire.MESSAGE";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static Camera cam = null;
    static boolean LIGHT_ON = false;

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
        EditText et = findViewById(R.id.text_input);
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

    public void toggleLight(View view) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Log.d("whj", String.format("Number of cameras: %d", numberOfCameras));
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Log.d("whj", "Camera flash hardware available.");
            if (!LIGHT_ON) {
                try {
                    cam = Camera.open(10);
                    Camera.Parameters p = cam.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam.setParameters(p);
                    cam.startPreview();
                    cam.autoFocus(new Camera.AutoFocusCallback() {
                        public void onAutoFocus(boolean success, Camera camera) {
                        }
                    });
                    Log.d("whj", "Turn on camera light.");
                    LIGHT_ON = true;
                } catch (Exception e) {
                    Log.d("whj", "Failed to open camera.");
                    e.printStackTrace();
                }
            } else {
                cam.stopPreview();
                cam.release();
                cam = null;
                Log.d("whj", "Turn off camera light.");
                LIGHT_ON = false;
            }
        }
    }
}
