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

import com.dmitrysukhov.secondhomeworkapp.main.FragmentCallback;
import com.dmitrysukhov.secondhomeworkapp.R;

public class FirstFragment extends Fragment {
    public static final String FIRST_FRAGMENT_TAG = "firstFragment";
    private FragmentCallback fragmentCallback;
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String textFromSecondFragment = bundle.getString(HostFragment.TEXT_FROM_SECOND_FRAGMENT_TAG);
            TextView textViewOfFirstFragment = view.findViewById(R.id.text_view_first_fragment);
            textViewOfFirstFragment.setText(textFromSecondFragment);
        }
        if (getParentFragment() instanceof FragmentCallback) {
            fragmentCallback = (FragmentCallback) getParentFragment();
        }
        editText = view.findViewById(R.id.edit_text_first_fragment);
        view.findViewById(R.id.button_send_to_second_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentCallback != null) {
                    if (!editText.getText().toString().equals(""))
                        fragmentCallback.sendTextToAnotherFragment(FIRST_FRAGMENT_TAG, editText.getText().toString());
                }
            }
        });
    }
}