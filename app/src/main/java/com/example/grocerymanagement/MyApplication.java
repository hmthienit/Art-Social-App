package com.example.grocerymanagement;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.Application;
import android.content.Context;

import com.example.grocerymanagement.api.config.CloudinaryConfig;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CloudinaryConfig.initCloudinary();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
