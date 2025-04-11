package com.example.grocerymanagement.views.ui.home.user;

import android.os.Bundle;

import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.ActivityUserDetailBinding;
import com.example.grocerymanagement.views.ui.home.user.model.Client;

public class UserDetailActivity extends BaseActivity<ActivityUserDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ActivityUserDetailBinding getViewBinding() {
        return ActivityUserDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        Client client = (Client) getIntent().getSerializableExtra(AppConstant.CLIENTS);
        clientViewModel.setClient(client);
    }

    @Override
    protected void initAction() {

    }
}