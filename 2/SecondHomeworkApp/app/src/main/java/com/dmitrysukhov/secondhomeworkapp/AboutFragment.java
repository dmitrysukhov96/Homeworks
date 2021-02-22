package com.dmitrysukhov.secondhomeworkapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AboutFragment extends DialogFragment implements MainActivity.AboutFragmentCallback {

    String textForToast;
    EditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.createdBy))
                .setView(R.layout.fragment_about)
                .setPositiveButton(getString(R.string.send), (dialog, which) -> {
                    passTextToToast();
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                })
                .create();
    }

    public static String TAG = "AboutDialogFragment";

    @Override
    public void passTextToToast() {
        editText = getDialog().findViewById(R.id.editText_about);
        textForToast = editText.getText().toString();
        Toast.makeText(getActivity(), textForToast, Toast.LENGTH_LONG).show();
    }
}
