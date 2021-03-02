package com.dmitrysukhov.fifthtask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "lifecycle_logs.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addLineToLogFile("onCreate");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File lifecycleLogsFile = new File(getFilesDir(), FILE_NAME);
                if (!lifecycleLogsFile.exists()) {
                    Toast.makeText(MainActivity.this, "Файл не существует", Toast.LENGTH_LONG).show();
                    return;
                }
                try {

                    Uri uri = FileProvider.getUriForFile(MainActivity.this,
                            "com.dmitrysukhov.fifthtask.fileprovider",
                            new File(getFilesDir(), FILE_NAME));
                    if (uri != null) {
                        Intent intentShare = new Intent(Intent.ACTION_SEND,uri);
                        intentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(intentShare, "Share file with..."));
                    }
                } catch (IllegalArgumentException e) {
                    Log.e("myTag",
                            "The selected file can't be shared: " + lifecycleLogsFile.toString());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        addLineToLogFile("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        addLineToLogFile("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        addLineToLogFile("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        addLineToLogFile("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addLineToLogFile("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addLineToLogFile("onRestart");
    }

    public void addLineToLogFile(String text) {
        File lifecycleLogsFile = new File(getFilesDir(), FILE_NAME);
        if (!lifecycleLogsFile.exists()) {
            try {
                lifecycleLogsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(lifecycleLogsFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}