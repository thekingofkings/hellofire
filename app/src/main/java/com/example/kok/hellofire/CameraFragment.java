package com.example.kok.hellofire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

/**
 * The second tab of the app -- Camera Tab
 *
 * It uses the layout {@link R.layout.fragment_camera}.
 *
 * There are two functions implemented:
 *  1. Open the pre-defined image capture intent with `camera` button.
 *  2. Turn on the camera light with `torch` button.
 */
public class CameraFragment extends Fragment implements CameraXConfig.Provider, View.OnClickListener {

    private static final int MY_REQUEST_CODE = 222;
    static Camera camera = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static boolean LIGHT_ON = false;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFut;
    private CameraSelector torchCameraSelector;
    private Preview torchPreview;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        }

        cameraProviderFut = ProcessCameraProvider.getInstance(
                requireActivity().getApplicationContext());
        torchCameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        torchPreview = new Preview.Builder().build();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("whj", "Now user should be able to use camera.");
            } else {
                Log.d("whj", "Your app will not have this permission.");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);

        Button sendButton = v.findViewById(R.id.button_camera);
        sendButton.setOnClickListener(this);

        Button timeButton = v.findViewById(R.id.button_light);
        timeButton.setOnClickListener(this);
        return v;
    }

    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }


    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void toggleLight(View view) {
        if (requireActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            // toggle light
            LIGHT_ON = !LIGHT_ON;
            cameraProviderFut.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFut.get();
                    PreviewView previewView = requireView().findViewById(R.id.torchPreviewView);
                    torchPreview.setSurfaceProvider(previewView.getSurfaceProvider());
                    camera = cameraProvider.bindToLifecycle(
                            (LifecycleOwner) this,
                            torchCameraSelector,
                            torchPreview
                    );

                    camera.getCameraControl().enableTorch(LIGHT_ON);
                    Log.d("whj", "Toggle camera light.");
                    if (!LIGHT_ON) {
                        cameraProvider.unbind(torchPreview);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }, ContextCompat.getMainExecutor(getActivity()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_camera:
                openCamera(view);
                break;
            case R.id.button_light:
                toggleLight(view);
                break;
            default:
                break;
        }
    }
}