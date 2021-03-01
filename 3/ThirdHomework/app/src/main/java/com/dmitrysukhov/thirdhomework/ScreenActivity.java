package com.dmitrysukhov.thirdhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ScreenActivity extends AppCompatActivity {

    public final static String GPS_PERMISSION_TAG = "isGpsPermissionGranted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        ImageView imageViewSun = findViewById(R.id.image_view_screen_sun);
        Animation rotation = AnimationUtils.loadAnimation(ScreenActivity.this, R.anim.rotate);
        rotation.setFillAfter(true);
        imageViewSun.startAnimation(rotation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!checkGpsPermission()) {
                    ActivityCompat.requestPermissions(ScreenActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                } else {
                    Intent intent = new Intent(ScreenActivity.this, MainActivity.class);
                    intent.putExtra(GPS_PERMISSION_TAG, checkGpsPermission());
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isGpsPermissionGranted", checkGpsPermission());
        startActivity(intent);
        finish();
    }

    private boolean checkGpsPermission() {
        boolean state = false;
        if (ActivityCompat.checkSelfPermission(ScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            state = true;
        } else if (ActivityCompat.checkSelfPermission(ScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            state = false;
        }
        return state;
    }
}