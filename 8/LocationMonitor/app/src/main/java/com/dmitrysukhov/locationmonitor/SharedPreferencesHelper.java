package com.dmitrysukhov.locationmonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

class SharedPreferencesHelper extends Application {

    private static SharedPreferences sharedPrefsSingleton;

    public static void getInstance(Context context) {
        if (sharedPrefsSingleton == null) {
            sharedPrefsSingleton = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
    }

    public static Set<String> getPrefStringSetByKey (String key){
        return sharedPrefsSingleton.getStringSet(key,null);
    }

    public static void addNewLocationToPrefs (String key, String location){
        SharedPreferences.Editor editor = sharedPrefsSingleton.edit();
        Set<String> stringSet = sharedPrefsSingleton.getStringSet(key,new HashSet<>());
        stringSet.add(location);
        editor.putStringSet(key,stringSet);
        editor.apply();
    }
}
