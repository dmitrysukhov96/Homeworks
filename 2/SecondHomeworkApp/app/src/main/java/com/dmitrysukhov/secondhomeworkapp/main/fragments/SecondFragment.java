package com.dmitrysukhov.secondhomeworkapp.main.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dmitrysukhov.secondhomeworkapp.R;
import com.dmitrysukhov.secondhomeworkapp.main.FragmentCallback;

public class SecondFragment extends Fragment {
    public static final String SECOND_FRAGMENT_TAG = "secondFragment";
    private FragmentCallback fragmentCallback;
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String textFromFirstFragment = bundle.getString(HostFragment.TEXT_FROM_FIRST_FRAGMENT_TAG);
            TextView textViewOfSecondFragment = view.findViewById(R.id.text_view_second_fragment);
            textViewOfSecondFragment.setText(textFromFirstFragment);
        }
        if (getParentFragment() instanceof FragmentCallback) {
            fragmentCallback = (FragmentCallback) getParentFragment();
        }
        editText = view.findViewById(R.id.edit_text_second_fragment);
        view.findViewById(R.id.button_send_to_first_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentCallback != null) {
                    if (!editText.getText().toString().equals(""))
                        fragmentCallback.sendTextToAnotherFragment(SECOND_FRAGMENT_TAG, editText.getText().toString());
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}