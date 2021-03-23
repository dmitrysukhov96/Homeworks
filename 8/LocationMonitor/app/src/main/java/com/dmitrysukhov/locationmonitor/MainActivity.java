package com.dmitrysukhov.locationmonitor;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String NEW_LOCATION_KEY = "location";
    public static final String BROADCAST_ACTION = "Location monitor";
    private Button buttonStopStartService;
    private BroadcastReceiver broadcastReceiverOfActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesHelper.getInstance(this);
        ((Button) findViewById(R.id.button_main_stop_service)).setText(getString(R.string.start_service));

        ArrayList<MyLocation> myLocationArrayList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main_locations);
        LocationRecyclerViewAdapter locationRecyclerViewAdapter = new LocationRecyclerViewAdapter(myLocationArrayList);
        recyclerView.setAdapter(locationRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Set<String> stringSet = SharedPreferencesHelper.getPrefStringSetByKey(NEW_LOCATION_KEY);
        if (stringSet != null) {
            for (String str : stringSet) {
                String[] stringArray = str.split("@");
                MyLocation myLocation = new MyLocation(stringArray[0], stringArray[1]);
                myLocationArrayList.add(myLocation);
            }
        }
        recyclerView.setAdapter(locationRecyclerViewAdapter);

        if (!arePermissionsGranted()) {
            requestRequiredPermissions();
        } else {
            if (!isGpsOn()) requireGps();
        }


        broadcastReceiverOfActivity = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String location = intent.getStringExtra(MainActivity.NEW_LOCATION_KEY);
                if (location != null) {
                    SharedPreferencesHelper.addNewLocationToPrefs(NEW_LOCATION_KEY, location);
                    locationRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        };
        registerReceiver(broadcastReceiverOfActivity, new IntentFilter(BROADCAST_ACTION));

        buttonStopStartService = findViewById(R.id.button_main_stop_service);
        buttonStopStartService.setOnClickListener(view -> {
            String text = buttonStopStartService.getText().toString();

            if (arePermissionsGranted()) {
                if (isGpsOn()) {
                    if (text.equals(getString(R.string.stop_service))) {
                        stopService(new Intent(MainActivity.this, LocationMonitorService.class));
                        buttonStopStartService.setText(R.string.start_service);
                    } else if (text.equals(getString(R.string.start_service))) {
                        startMyLocationMonitorService();
                        buttonStopStartService.setText(R.string.stop_service);
                    }
                } else requireGps();
            } else requestRequiredPermissions();
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiverOfActivity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 12) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED)
                new AlertDialog.Builder(this)
                        .setTitle(R.string.gps_rationale)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION}, 12);
                                    }
                                }
                        ).setNegativeButton(R.string.cancel, null)
                        .show();
        } else {
            if (isGpsOn()) startMyLocationMonitorService();
            else requireGps();
        }
    }

    private boolean arePermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isGpsOn() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    void startMyLocationMonitorService() {
        Intent intent = new Intent(this, LocationMonitorService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(intent);
        } else startService(intent);
    }

    private void requestRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 12);
        }
    }

    private void requireGps() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.please_turn_on_gps)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                ).setNegativeButton(R.string.no_thanks, null)
                .show();
    }
}