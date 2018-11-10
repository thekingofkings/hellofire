package com.example.kok.hellofire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class HelloFireActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    public static final String EXTRA_MESSAGE = "com.example.kok.hellofire.MESSAGE";
    private static int MY_REQUEST_CODE = 20181110;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static Camera camera = null;
    static SurfaceHolder cameraPreviewHolder = null;
    static boolean LIGHT_ON = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_fire);
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("whj", "Now user should be able to use camera.");
            }
            else {
                Log.d("whj", "Your app will not have this permission.");
            }
        }
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
        SurfaceView preview = findViewById(R.id.camera_light_view);
        cameraPreviewHolder = preview.getHolder();
        cameraPreviewHolder.addCallback(this);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            if (!LIGHT_ON) {
                try {
                    camera = Camera.open(0);
                    Camera.Parameters p = camera.getParameters();
                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
                    camera.autoFocus(new Camera.AutoFocusCallback() {
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
                camera.stopPreview();
                camera.release();
                camera = null;
                Log.d("whj", "Turn off camera light.");
                LIGHT_ON = false;
            }
        }
    }

    /**
     * ========================================================
     * Implement abstract function for SurfaceHolder.Callback
     * ========================================================
     */
    public void surfaceCreated(SurfaceHolder holder) {
        cameraPreviewHolder = holder;
        try {
            camera.setPreviewDisplay(cameraPreviewHolder);
        } catch (Exception e) {
            Log.d("whj", "Failed to set preview holder for camera.");
            e.printStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // deliberately empty
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraPreviewHolder = null;
    }

}
