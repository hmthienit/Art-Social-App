package com.example.grocerymanagement.views.ui.home.home;

import android.os.Bundle;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.ActivityProductDetailBinding;
import com.example.grocerymanagement.views.ui.home.home.model.Product;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding> {

    public Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected ActivityProductDetailBinding getViewBinding() {
        return ActivityProductDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        product = (Product) getIntent().getSerializableExtra(AppConstant.PRODUCT);
        productViewModel.setProduct(product);
    }

    @Override
    protected void initAction() {

    }
}