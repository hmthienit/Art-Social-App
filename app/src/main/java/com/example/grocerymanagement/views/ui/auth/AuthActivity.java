package com.example.grocerymanagement.views.ui.auth;

import android.os.Bundle;

import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.databinding.ActivityAuthBinding;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ActivityAuthBinding getViewBinding() {
        return ActivityAuthBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {

    }
}