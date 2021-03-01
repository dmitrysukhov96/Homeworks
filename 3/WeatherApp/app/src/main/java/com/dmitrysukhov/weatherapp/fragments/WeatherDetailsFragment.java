package com.dmitrysukhov.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.adapters.GridViewAdapter;
import com.dmitrysukhov.weatherapp.R;
import com.dmitrysukhov.weatherapp.adapters.RecyclerAdapterDetails;

public class WeatherDetailsFragment extends Fragment {

    int[] imagesSunMoon = {R.drawable.ic_sun_yellow,R.drawable.ic_sun_yellow,R.drawable.ic_sun_yellow,R.drawable.ic_moon_yellow,R.drawable.ic_moon_yellow};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_details_weather);

        String[] stringArrayDetailsTime = getResources().getStringArray(R.array.time_details);
        String[] stringArrayDetailsWeather = getResources().getStringArray(R.array.weather_details);
        String[] stringArrayDetailsWindSpeed = getResources().getStringArray(R.array.wind_speed_details);

        RecyclerAdapterDetails recyclerAdapterDetails = new RecyclerAdapterDetails(requireContext(), stringArrayDetailsTime, stringArrayDetailsWeather, stringArrayDetailsWindSpeed, imagesSunMoon);
        recyclerView.setAdapter(recyclerAdapterDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false));
        GridView gridview = getView().findViewById(R.id.grid_view_details);
        if (gridview!=null){
        gridview.setAdapter(new GridViewAdapter(requireContext()));}
    }
}