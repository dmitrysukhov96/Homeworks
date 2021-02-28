package com.dmitrysukhov.secondhomeworkapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dmitrysukhov.secondhomeworkapp.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String MESSAGE_TAG = "messageForAlert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout_settings_container, new SettingsFragment(), SettingsFragment.SETTINGS_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager()
                .findFragmentByTag(SettingsFragment.SETTINGS_FRAGMENT_TAG);
        String resultsOfSettings = settingsFragment.returnResults();
        Intent intent = new Intent();
        intent.putExtra(MESSAGE_TAG, resultsOfSettings);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}