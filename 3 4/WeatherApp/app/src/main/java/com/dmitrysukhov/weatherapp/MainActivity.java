package com.dmitrysukhov.weatherapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dmitrysukhov.weatherapp.adapters.ScreenSlidePagerAdapter;
import com.dmitrysukhov.weatherapp.fragments.WeatherMainFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements WeatherMainFragment.MainFragmentCallback {

    private DrawerLayout drawerLayoutMain;
    private Toolbar toolbarMain;
    private SwipeRefreshLayout swipeRefreshLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarMain = findViewById(R.id.toolbar_main_activity);
        Intent intent = getIntent();
        if (intent.getBooleanExtra(ScreenActivity.GPS_PERMISSION_TAG, false)) {
            setCityIntoToolbar();
        } else {
            toolbarMain.setTitle(R.string.app_name);
        }
        setSupportActionBar(toolbarMain);

        drawerLayoutMain = findViewById(R.id.drawer_layout_main_container);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayoutMain, toolbarMain, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayoutMain.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();

        ViewPager2 viewPager = findViewById(R.id.view_pager_main_activity_container);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout_main_activity_switch_fragments);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Fragment " + (position + 1))
        ).attach();


        NavigationView navView = findViewById(R.id.nav_view_main_activity);
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.nav_menu_item_main): {
                    Toast.makeText(MainActivity.this, getString(R.string.main_activity), Toast.LENGTH_SHORT).show();
                    drawerLayoutMain.closeDrawer(GravityCompat.START);
                    break;
                }
                case (R.id.nav_menu_item_settings): {
                    Toast.makeText(MainActivity.this, getString(R.string.settings_activity), Toast.LENGTH_SHORT).show();
                    drawerLayoutMain.closeDrawer(GravityCompat.START);
                    break;
                }
            }
            return false;
        });

        swipeRefreshLayoutMain = findViewById(R.id.swipe_refresh_layout_main_container);
        swipeRefreshLayoutMain.setOnRefreshListener(() -> {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            } else {
                setCityIntoToolbar();
            }
            swipeRefreshLayoutMain.setRefreshing(false);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setCityIntoToolbar();
        }
    }

    private void setCityIntoToolbar() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location != null) {
                try {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses;
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    toolbarMain.setTitle(addresses.get(0).getLocality());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.turn_on_gps_please), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutMain.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    @Override
    public void navigateToSecondFragment() {
        TabLayout tabLayout = findViewById(R.id.tab_layout_main_activity_switch_fragments);
        tabLayout.selectTab(tabLayout.getTabAt(1));
    }
}


