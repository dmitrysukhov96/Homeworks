package com.dmitrysukhov.secondhomeworkapp.about;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dmitrysukhov.secondhomeworkapp.R;

public class AboutFragment extends DialogFragment {
    public static final String ABOUT_DIALOG_TAG = "AboutDialogFragment";
    private EditText editText;
    private AboutFragmentCallback callback;

    public interface AboutFragmentCallback {
        void passTextToToast(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AboutFragmentCallback) {
            callback = (AboutFragmentCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.createdBy))
                .setView(R.layout.fragment_about)
                .setPositiveButton(getString(R.string.send), (dialog, which) -> {
                    editText = getDialog().findViewById(R.id.editText_about);
                    if (!editText.getText().toString().equals("")) {
                        callback.passTextToToast(editText.getText().toString());
                    }
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                })
                .create();
    }
}