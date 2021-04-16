package com.dmitrysukhov.loginapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dmitrysukhov.loginapp.R;
import com.dmitrysukhov.loginapp.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding fragmentSettingsBinding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        return fragmentSettingsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.my_settings_list_item, getResources().getStringArray(R.array.settings_entries));
        fragmentSettingsBinding.listViewSettings.setAdapter(adapter); //TODO тут почему то падает
        fragmentSettingsBinding.listViewSettings.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0) {
                Bundle updateFlag = new Bundle();
                updateFlag.putBoolean("update",true);
                navController.navigate(R.id.profileInfoFragment,updateFlag);
            }
        });
        ((Toolbar) fragmentSettingsBinding.toolbarSettings.getRoot()).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment.this.requireActivity().onBackPressed();
            }
        });

    }
}