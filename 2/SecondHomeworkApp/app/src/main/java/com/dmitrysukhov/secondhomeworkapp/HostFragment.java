package com.dmitrysukhov.secondhomeworkapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HostFragment extends Fragment {

    Fragment fragment1 = new Fragment1();
    Fragment fragment2 = new Fragment2();

    public HostFragment() {
    }

    public static HostFragment newInstance(String param1, String param2) {
        HostFragment fragment = new HostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_host_container, fragment2, "fragment2")
                .replace(R.id.frameLayout_host_container, fragment1, "fragment1").commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_host, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}