package com.example.grocerymanagement.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "app_prefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String TOKEN  = "TOKEN";


    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setToken(String token){
        saveString(TOKEN, token);
    }

    public String getToken(){
        return getString(TOKEN, "");
    }

    public void removeDataLoggOut(){
        remove(TOKEN);
    }

    // Save a string value
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply(); // or editor.commit();
    }

    // Retrieve a string value
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save an integer value
    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply(); // or editor.commit();
    }

    // Retrieve an integer value
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Save a boolean value
    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply(); // or editor.commit();
    }

    // Retrieve a boolean value
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Remove a value
    public void remove(String key) {
        editor.remove(key);
        editor.apply(); // or editor.commit();
    }

    // Clear all values
    public void clear() {
        editor.clear();
        editor.apply(); // or editor.commit();
    }
}
