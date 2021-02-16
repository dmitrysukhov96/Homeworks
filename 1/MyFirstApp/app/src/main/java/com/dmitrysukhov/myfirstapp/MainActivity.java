package com.dmitrysukhov.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.dmitrysukhov.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MyTag onCreate", "onCreate");
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MyTag onStart", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MyTag onResume", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MyTag onPause", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MyTag onStop", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MyTag onRestart", "onRestart");
    }
}