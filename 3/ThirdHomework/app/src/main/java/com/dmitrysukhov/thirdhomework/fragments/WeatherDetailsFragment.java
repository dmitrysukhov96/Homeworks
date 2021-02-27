package com.dmitrysukhov.thirdhomework.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.thirdhomework.R;
import com.dmitrysukhov.thirdhomework.SecondAdapter;
import com.google.android.material.tabs.TabLayout;

public class WeatherDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private String[] stringArray1, stringArray2, stringArray3;
    int[] images = {R.drawable.ic_sun_yellow,R.drawable.ic_sun_yellow,R.drawable.ic_sun_yellow,R.drawable.ic_moon_yellow,R.drawable.ic_moon_yellow};

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
        recyclerView = view.findViewById(R.id.recycler_view_details_weather);

        stringArray1 = getResources().getStringArray(R.array.time_details);
        stringArray2 = getResources().getStringArray(R.array.weather_details);
        stringArray3 = getResources().getStringArray(R.array.wind_speed_details);

        SecondAdapter secondAdapter = new SecondAdapter(getActivity(), stringArray1, stringArray2, stringArray3, images);
        recyclerView.setAdapter(secondAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
    }
}