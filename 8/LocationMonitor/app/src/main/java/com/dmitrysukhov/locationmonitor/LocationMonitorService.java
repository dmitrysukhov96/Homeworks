package com.dmitrysukhov.locationmonitor;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationMonitorService extends Service implements LocationListener {

    private final String NOTIFICATION_CHANNEL_ID = "notification_channel_location";
    private BroadcastReceiver broadcastReceiverOfService;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String NOTIFICATION_CHANNEL_NAME = getString(R.string.notification_channel_location);
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
        }
        Notification notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getString(R.string.location_monitor_service)).setContentText(getString(R.string.service_is_launched)).setSmallIcon(R.drawable.ic_location).build();
        startForeground(1, notification);

        broadcastReceiverOfService = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String location = intent.getStringExtra(MainActivity.NEW_LOCATION_KEY);
                if (location != null) {
                    SharedPreferencesHelper.addNewLocationToPrefs(MainActivity.NEW_LOCATION_KEY, location);
                }
            }
        };
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String coordinates = location.getLatitude() + ", " + location.getLongitude();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String addressLine = addresses.get(0).getAddressLine(0);
            createNewNotification(coordinates, addressLine);
            sendMyBroadcast(coordinates + "@" + addressLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMyBroadcast(String location) {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra(MainActivity.NEW_LOCATION_KEY, location);
        sendBroadcast(intent);
    }

    private void createNewNotification(String coordinates, String address) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("New location: "+coordinates).setContentText(address)
                    .setSmallIcon(R.drawable.ic_location).setContentIntent(pendingIntent).build();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //android studio just required this if statement
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiverOfService);
    }
}
