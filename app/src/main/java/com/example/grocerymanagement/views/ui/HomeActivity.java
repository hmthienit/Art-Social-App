package com.example.grocerymanagement.views.ui;

import android.os.Bundle;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.databinding.ActivityHomeBinding;
import com.example.grocerymanagement.views.ui.home.order.OrderActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class HomeActivity extends BaseActivity<ActivityHomeBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_test);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {
        binding.btOrder.setOnClickListener(view -> startActivity(OrderActivity.class));
    }

}