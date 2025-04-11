package com.example.grocerymanagement.views.ui.home.order;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.databinding.ActivityOrderBinding;

public class OrderActivity extends BaseActivity<com.example.grocerymanagement.databinding.ActivityOrderBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected com.example.grocerymanagement.databinding.ActivityOrderBinding getViewBinding() {
        return com.example.grocerymanagement.databinding.ActivityOrderBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {

    }
}