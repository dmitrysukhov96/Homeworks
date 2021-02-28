package com.dmitrysukhov.secondhomeworkapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.dmitrysukhov.secondhomeworkapp.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String SETTINGS_FRAGMENT_TAG = "settingsFragment";
    private final String loginTag = "login";
    private final String showLoginTag = "show_login";
    private final String switchNightModeTag = "switch_night_mode";
    private final String whoWillSeeNumberTag = "who_will_see_number";
    private EditTextPreference loginEditTextPreference;
    private String oldLoginValue;
    private CheckBoxPreference showLoginCheckBoxPreference;
    private boolean oldShowLoginValue;
    private SwitchPreference nightModeSwitchPreference;
    private boolean oldNightModeValue;
    private ListPreference whoWillSeeNumbersListPreference;
    private String oldWhoWillListChosenValue;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        loginEditTextPreference = getPreferenceManager().findPreference(loginTag);
        showLoginCheckBoxPreference = getPreferenceManager().findPreference(showLoginTag);
        nightModeSwitchPreference = getPreferenceManager().findPreference(switchNightModeTag);
        whoWillSeeNumbersListPreference = getPreferenceManager().findPreference(whoWillSeeNumberTag);
        oldLoginValue = loginEditTextPreference.getText();
        oldShowLoginValue = showLoginCheckBoxPreference.isChecked();
        oldNightModeValue = nightModeSwitchPreference.isChecked();
        oldWhoWillListChosenValue = whoWillSeeNumbersListPreference.getValue();
        super.onViewCreated(view, savedInstanceState);
    }

    public String returnResults() {
        String results = getString(R.string.changed_settings);
        if (loginEditTextPreference.getText() != oldLoginValue) {
            results += "\n" + getString(R.string.your_login_title) + ": " + loginEditTextPreference.getText();
        }
        if (showLoginCheckBoxPreference.isChecked() != oldShowLoginValue) {
            results += "\n" + getString(R.string.show_login_title) + ": " + showLoginCheckBoxPreference.isChecked();
        }
        if (nightModeSwitchPreference.isChecked() != oldNightModeValue) {
            results += "\n" + getString(R.string.night_mode) + ": " + nightModeSwitchPreference.isChecked();
        }
        if (whoWillSeeNumbersListPreference.getValue() != oldWhoWillListChosenValue) {
            results += "\n" + getString(R.string.who_will_see_number_title) + ": " + whoWillSeeNumbersListPreference.getValue();
        }
        if (results.equals(getString(R.string.changed_settings))) {
            results = null;
        }
        return results;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (getView() != null) {
            Snackbar.make(getView(), R.string.settings_changed,
                    BaseTransientBottomBar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (s) {
                        case (loginTag): {
                            loginEditTextPreference.setText(oldLoginValue);
                            break;
                        }
                        case (showLoginTag): {
                            showLoginCheckBoxPreference.setChecked(oldShowLoginValue);
                            break;
                        }
                        case (switchNightModeTag): {
                            nightModeSwitchPreference.setChecked(oldNightModeValue);
                            break;
                        }
                        case (whoWillSeeNumberTag): {
                            whoWillSeeNumbersListPreference.setValue(oldWhoWillListChosenValue);
                            break;
                        }
                    }
                }
            }).show();
        }
    }
}