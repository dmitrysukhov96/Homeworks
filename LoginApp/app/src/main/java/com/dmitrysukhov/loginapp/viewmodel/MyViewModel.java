package com.dmitrysukhov.loginapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.dmitrysukhov.loginapp.database.User;
import com.dmitrysukhov.loginapp.database.UserDatabase;

public class MyViewModel extends ViewModel {
    private UserDatabase db;
    private User currentUser;

    public User getCurrentUser() {
        if (currentUser == null) currentUser = new User();
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
