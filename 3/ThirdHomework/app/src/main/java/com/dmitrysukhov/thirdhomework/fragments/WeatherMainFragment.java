package com.dmitrysukhov.thirdhomework.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dmitrysukhov.thirdhomework.adapters.RecyclerAdapterMain;
import com.dmitrysukhov.thirdhomework.R;

public class WeatherMainFragment extends Fragment {

    public interface MainFragmentCallback {
        void navigateToSecondFragment();
    }

    private MainFragmentCallback mainFragmentCallback;
    int[] imagesAirCloudSun = {R.drawable.ic_air, R.drawable.ic_cloud, R.drawable.ic_sun_yellow};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewMain = view.findViewById(R.id.recycler_view_main_fragment_recent_days);

        String[] stringArrayMainDays = getResources().getStringArray(R.array.day_main);
        String[] stringArrayMainWeather = getResources().getStringArray(R.array.weather_main);
        String[] stringArrayMainTemperature = getResources().getStringArray(R.array.temperature_main);

        RecyclerAdapterMain recyclerAdapterMain = new RecyclerAdapterMain(requireContext(), stringArrayMainDays, stringArrayMainWeather, stringArrayMainTemperature, imagesAirCloudSun);
        recyclerViewMain.setAdapter(recyclerAdapterMain);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (requireContext() instanceof MainFragmentCallback) {
            mainFragmentCallback = (MainFragmentCallback) requireContext();
        }

        LinearLayout linearLayoutMainMore = view.findViewById(R.id.linearLayout_main_fragment_more_details);
        linearLayoutMainMore.setOnClickListener(view1 -> {
            if (mainFragmentCallback != null) {
                mainFragmentCallback.navigateToSecondFragment();
            }
        });

    }
}