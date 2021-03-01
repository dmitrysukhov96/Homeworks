package com.dmitrysukhov.secondhomeworkapp.main.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmitrysukhov.secondhomeworkapp.main.FragmentCallback;
import com.dmitrysukhov.secondhomeworkapp.R;

public class HostFragment extends Fragment implements FragmentCallback {

    public static final String HOST_FRAGMENT_TAG = "hostFragment";
    public static final String TEXT_FROM_FIRST_FRAGMENT_TAG = "text_from_first_fragment";
    public static final String TEXT_FROM_SECOND_FRAGMENT_TAG = "text_from_second_fragment";

    @Override
    public void sendTextToAnotherFragment(String tagOfFragmentSender, String text) {
        switch (tagOfFragmentSender) {
            case FirstFragment.FIRST_FRAGMENT_TAG: {
                Bundle bundle = new Bundle();
                bundle.putString(TEXT_FROM_FIRST_FRAGMENT_TAG, text);
                SecondFragment secondFragment = new SecondFragment();
                secondFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.framelayout_host_container, secondFragment, SecondFragment.SECOND_FRAGMENT_TAG).commit();
                break;
            }
            case SecondFragment.SECOND_FRAGMENT_TAG: {
                Bundle bundle = new Bundle();
                bundle.putString(TEXT_FROM_SECOND_FRAGMENT_TAG, text);
                FirstFragment firstFragment = new FirstFragment();
                firstFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.framelayout_host_container, firstFragment, FirstFragment.FIRST_FRAGMENT_TAG).commit();
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.framelayout_host_container, new FirstFragment(), FirstFragment.FIRST_FRAGMENT_TAG)
                    .commit();
        }
    }

    public void showFragment(String tagOfFragment) {
        if (getChildFragmentManager().findFragmentByTag(tagOfFragment) == null) {
            switch (tagOfFragment) {
                case (FirstFragment.FIRST_FRAGMENT_TAG): {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.framelayout_host_container, new FirstFragment(), FirstFragment.FIRST_FRAGMENT_TAG).commit();
                    break;
                }
                case SecondFragment.SECOND_FRAGMENT_TAG: {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.framelayout_host_container, new SecondFragment(), SecondFragment.SECOND_FRAGMENT_TAG).commit();
                    break;
                }
            }
        }
    }
}