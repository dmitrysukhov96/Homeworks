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

public class FirstFragment extends Fragment implements HostFragment.MyFragmentCallback {

    public static final String TAG = "firstFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String textFromSecondFragment = bundle.getString("text_from_second_fragment");
            TextView textViewOfFirstFragment = view.findViewById(R.id.textview_first_fragment);
            textViewOfFirstFragment.setText(textFromSecondFragment);
        }
        getActivity().findViewById(R.id.button_send_to_second_fragment).setOnClickListener(new View.OnClickListener() {
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
        EditText editText = getActivity().findViewById(R.id.edittext_first_fragment);
        return editText.getText().toString();
    }


}