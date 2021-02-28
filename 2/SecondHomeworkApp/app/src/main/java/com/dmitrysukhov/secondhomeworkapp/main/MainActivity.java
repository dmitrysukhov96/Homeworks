package com.dmitrysukhov.secondhomeworkapp.main;

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
import android.widget.Toast;

import com.dmitrysukhov.secondhomeworkapp.about.AboutFragment;
import com.dmitrysukhov.secondhomeworkapp.R;
import com.dmitrysukhov.secondhomeworkapp.main.fragments.FirstFragment;
import com.dmitrysukhov.secondhomeworkapp.main.fragments.HostFragment;
import com.dmitrysukhov.secondhomeworkapp.main.fragments.SecondFragment;
import com.dmitrysukhov.secondhomeworkapp.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements AboutFragment.AboutFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main_activity_container, new HostFragment(), HostFragment.HOST_FRAGMENT_TAG)
                    .commit();
        }
        findViewById(R.id.button_main_first_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HostFragment hostFragment = (HostFragment) getSupportFragmentManager().findFragmentByTag(HostFragment.HOST_FRAGMENT_TAG);
                hostFragment.showFragment(FirstFragment.FIRST_FRAGMENT_TAG);
            }
        });
        findViewById(R.id.button_main_second_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HostFragment hostFragment = (HostFragment) getSupportFragmentManager().findFragmentByTag(HostFragment.HOST_FRAGMENT_TAG);
                hostFragment.showFragment(SecondFragment.SECOND_FRAGMENT_TAG);
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
            case (R.id.item_menu_about): {
                new AboutFragment().show(getSupportFragmentManager(), AboutFragment.ABOUT_DIALOG_TAG);
                break;
            }
            case (R.id.item_menu_settings): {
                mStartForResult.launch(new Intent(this, SettingsActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangedPreferences(Intent intent) {
        String messageForAlert = intent.getStringExtra(SettingsActivity.MESSAGE_TAG);
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
                    if (result.getData() != null) {
                        showChangedPreferences(result.getData());
                    }
                }
            });

    @Override
    public void passTextToToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}