package com.example.grocerymanagement.api.config;


import com.cloudinary.android.MediaManager;
import com.example.grocerymanagement.MyApplication;
import com.example.grocerymanagement.constants.AppConstant;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryConfig {
    public static void initCloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", AppConstant.CLOUD_NAME); // Replace with your Cloudinary cloud name
        config.put("api_key", AppConstant.CLOUD_API_KEY);       // Replace with your API key
        config.put("api_secret", AppConstant.CLOUD_SECRET); // Replace with your API secret
        MediaManager.init(MyApplication.getContext(), config); // Initialize MediaManager
    }
}
