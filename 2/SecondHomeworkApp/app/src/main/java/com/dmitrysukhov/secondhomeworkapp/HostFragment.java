package com.dmitrysukhov.secondhomeworkapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HostFragment extends Fragment {

    public static final String TAG = "hostFragment";

    public interface MyFragmentCallback {
        String passText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.framelayout_host_container, new FirstFragment(), FirstFragment.TAG)
                    .commit();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public void showFragment(String tagOfFragment) {
        if (getChildFragmentManager().findFragmentByTag(tagOfFragment) == null) {
            switch (tagOfFragment) {
                case (FirstFragment.TAG): {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.framelayout_host_container, new FirstFragment(), FirstFragment.TAG).commit();
                    break;
                }
                case SecondFragment.TAG: {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.framelayout_host_container, new SecondFragment(), SecondFragment.TAG).commit();
                    break;
                }
            }
        }
    }

    public void sendTextToFragment(String text) {
        switch (getChildFragmentManager().findFragmentById(R.id.framelayout_host_container).getTag()) {
            case (FirstFragment.TAG): {
                Bundle bundle = new Bundle();
                bundle.putString("text_from_first_fragment", text);
                SecondFragment secondFragment = new SecondFragment();
                secondFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.framelayout_host_container, secondFragment, SecondFragment.TAG).commit();
                break;
            }
            case (SecondFragment.TAG): {
                Bundle bundle = new Bundle();
                bundle.putString("text_from_second_fragment", text);
                FirstFragment firstFragment = new FirstFragment();
                firstFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.framelayout_host_container, firstFragment, FirstFragment.TAG).commit();
                break;
            }
        }
    }
}