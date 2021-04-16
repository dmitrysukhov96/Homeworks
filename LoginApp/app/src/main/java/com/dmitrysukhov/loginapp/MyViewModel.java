package com.dmitrysukhov.loginapp;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyViewModel extends ViewModel {
    private UserDatabase db;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public UserDatabase getUserDatabase(Context context) {
        if (db == null) {
            db = UserDatabase.getInstance(context);
        }
        return db;
    }

    public void saveCurrentUser(User user) {
        currentUser = user;
    }
}
