package com.dmitrysukhov.loginapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.dmitrysukhov.loginapp.databinding.FragmentMainScreenBinding;
import com.dmitrysukhov.loginapp.databinding.NavViewMainHeaderBinding;

public class MainScreenFragment extends Fragment {

    private MyViewModel myViewModel;
    private NavController navController;
    private FragmentMainScreenBinding fragmentMainScreenBinding;
    private NavViewMainHeaderBinding navViewMainHeaderBinding;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainScreenBinding = FragmentMainScreenBinding.inflate(inflater, container, false);
        return fragmentMainScreenBinding.getRoot();}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        navViewMainHeaderBinding = NavViewMainHeaderBinding.bind(fragmentMainScreenBinding.navViewMain.getHeaderView(0));
        navViewMainHeaderBinding.setUser(myViewModel.getCurrentUser());
        if (myViewModel.getCurrentUser().imageUri != null) {
            Glide.with(this)
                    .load(myViewModel.getCurrentUser().imageUri)
                    .into(navViewMainHeaderBinding.imageViewNav);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(requireActivity(), fragmentMainScreenBinding.drawerLayoutMain,
                (Toolbar) fragmentMainScreenBinding.toolbar.getRoot(),R.string.opened, R.string.closed);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        fragmentMainScreenBinding.drawerLayoutMain.addDrawerListener(actionBarDrawerToggle);
        fragmentMainScreenBinding.navViewMain.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.drawer_menu_item_settings) {
                navController.navigate(R.id.settingsFragment); //TODO не генерируется класс FragmentMainScreenDirections
            } else if (item.getItemId() == R.id.drawer_menu_item_log_out) {
                myViewModel.saveCurrentUser(null);
                navController.navigate(R.id.loginFragment); //TODO не генерируется класс FragmentMainScreenDirections
            }
            item.setChecked(true);
            fragmentMainScreenBinding.drawerLayoutMain.closeDrawers();
            return true;
        });
    }
}