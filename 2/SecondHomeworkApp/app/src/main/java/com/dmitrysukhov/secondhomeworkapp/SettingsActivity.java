package com.dmitrysukhov.secondhomeworkapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout_settings_container, new SettingsFragment(), "settings_fragment")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO
    }
}