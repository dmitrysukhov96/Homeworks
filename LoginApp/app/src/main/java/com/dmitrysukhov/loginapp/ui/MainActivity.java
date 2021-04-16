package com.dmitrysukhov.loginapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.dmitrysukhov.loginapp.viewmodel.MyViewModel;
import com.dmitrysukhov.loginapp.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "dima";
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
    }


}