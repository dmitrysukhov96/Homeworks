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

import com.dmitrysukhov.thirdhomework.RecyclerAdapterMain;
import com.dmitrysukhov.thirdhomework.R;
import com.google.android.material.tabs.TabLayout;

public class WeatherMainFragment extends Fragment {

    private RecyclerView recyclerView;
    private String[] stringArray1, stringArray2, stringArray3;
    int[] images = {R.drawable.ic_air, R.drawable.ic_cloud, R.drawable.ic_sun_yellow};

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
        recyclerView = view.findViewById(R.id.recycler_view_main_fragment_recent_days);

        stringArray1 = getResources().getStringArray(R.array.day_main);
        stringArray2 = getResources().getStringArray(R.array.weather_main);
        stringArray3 = getResources().getStringArray(R.array.temperature_main);

        RecyclerAdapterMain recyclerAdapterMain = new RecyclerAdapterMain(getActivity(), stringArray1, stringArray2, stringArray3, images);
        recyclerView.setAdapter(recyclerAdapterMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayout linearLayoutMainMore = view.findViewById(R.id.linearLayout_main_fragment_more_details);
        linearLayoutMainMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout_main_activity_switch_fragments);
                tabLayout.selectTab(tabLayout.getTabAt(1));
            }
        });

    }
}