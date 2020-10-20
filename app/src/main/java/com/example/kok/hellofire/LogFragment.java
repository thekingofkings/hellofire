package com.example.kok.hellofire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.Fragment;

public class LogFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_FILE_NAME = "local_log";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private FileOutputStream logFileOutput;
    private Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        File file = new File(getContext().getFilesDir() + LOG_FILE_NAME);
        Log.d("whj", file.getAbsolutePath());
        try {
            logFileOutput = getContext().openFileOutput(LOG_FILE_NAME, Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            Log.e("whj", "File not found");
        }
    }

    /**
     * This page looks and feels similar to the Info Fragment page. Therefore, we re-use that
     * fragment_info layout but change the button name and function here.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        Button writeButton = v.findViewById(R.id.button_display_info);
        writeButton.setText("Write log");
        writeButton.setOnClickListener(this);

        Button showButton = v.findViewById(R.id.button_time);
        showButton.setText("Show log");
        showButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onDestroy() {
        try {
            logFileOutput.close();
        } catch (IOException e) {
            Log.e("whj", "Could not close log file.");
        }
        super.onDestroy();
    }

    public void writeLogToFile(View view) {
        EditText et = getView().findViewById(R.id.text_input);
        String msg = et.getText().toString();
        String timestamp = sdf.format(calendar.getTime());
        String outputLine = String.format("%s: %s\n", timestamp, msg);
        try {
            logFileOutput.write(outputLine.getBytes());
        } catch (IOException e) {
            Log.e("whj", "Write failed");
            Toast.makeText(getActivity(), "Write to log failed!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), "Write to log successfully!", Toast.LENGTH_SHORT).show();
        et.getText().clear();
    }

    public void readLogFromFile(View view) {
        Intent intent = new Intent(getActivity(), ShowInfoActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        String contents = null;
        try {
            FileInputStream fio = getContext().openFileInput(LOG_FILE_NAME);
            BufferedReader freader = new BufferedReader(new InputStreamReader(fio, StandardCharsets.UTF_8));
            String line = freader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = freader.readLine();
            }
            contents = stringBuilder.toString();
            fio.close();
        } catch (IOException e) {
            Log.e("whj", "Read log error");
            contents = "Read failed!";
        } finally {
            intent.putExtra(InfoFragment.EXTRA_MESSAGE, contents);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_display_info:
                writeLogToFile(view);
                break;
            case R.id.button_time:
                readLogFromFile(view);
                break;
            default:
                break;
        }
    }
}
