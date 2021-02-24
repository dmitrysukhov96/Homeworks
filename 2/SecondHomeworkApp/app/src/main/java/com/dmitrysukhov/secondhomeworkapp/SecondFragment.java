package com.dmitrysukhov.secondhomeworkapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SecondFragment extends Fragment implements HostFragment.MyFragmentCallback {

    public static final String TAG = "secondFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String textFromFirstFragment = bundle.getString("text_from_first_fragment");
            TextView textViewOfSecondFragment = view.findViewById(R.id.textview_second_fragment);
            textViewOfSecondFragment.setText(textFromFirstFragment);
        }
        getActivity().findViewById(R.id.button_send_to_first_fragment).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                HostFragment hostFragment = (HostFragment) getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.container_main_for_fragments);
                hostFragment.sendTextToFragment(passText());
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public String passText() {
        EditText editText = getActivity().findViewById(R.id.edittext_second_fragment);
        return editText.getText().toString();
    }
}