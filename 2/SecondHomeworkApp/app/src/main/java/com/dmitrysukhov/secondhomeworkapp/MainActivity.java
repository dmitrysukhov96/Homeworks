package com.dmitrysukhov.secondhomeworkapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_main_for_fragments, new HostFragment(), "host_fragment")
                    .commit();
        }
        findViewById(R.id.button_main_first_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HostFragment hostFragment = (HostFragment) getSupportFragmentManager().findFragmentByTag("host_fragment");
                hostFragment.showFragment("firstFragment");
            }
        });
        findViewById(R.id.button_main_second_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HostFragment hostFragment = (HostFragment) getSupportFragmentManager().findFragmentByTag("host_fragment");
                hostFragment.showFragment("secondFragment");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.about): {
                new AboutFragment().show(getSupportFragmentManager(), AboutFragment.TAG);
                break;
            }
            case (R.id.settings): {
                mStartForResult.launch(new Intent(this, SettingsActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangedPreferences(Intent intent) {
        String messageForAlert = intent.getStringExtra("messageForAlert");
        if (messageForAlert != null) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(messageForAlert)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    })
                    .create().show();
        }
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();//intent ne null
                    if (result.getData() != null) {
                        showChangedPreferences(result.getData());
                    }
                }
            });

    public interface AboutFragmentCallback {
        void passTextToToast();
    }
}