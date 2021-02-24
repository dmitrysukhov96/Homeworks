package com.dmitrysukhov.secondhomeworkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout_settings_container, new SettingsFragment(), SettingsFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager()
                .findFragmentByTag(SettingsFragment.TAG);
        String resultsOfSettings = settingsFragment.returnResults();
        Intent intent = new Intent();
        intent.putExtra("messageForAlert", resultsOfSettings);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}