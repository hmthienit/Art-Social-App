package com.example.grocerymanagement.views.ui.home.sale;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.ActivitySaleDetailBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;

public class SaleDetailActivity extends BaseActivity<ActivitySaleDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ActivitySaleDetailBinding getViewBinding() {
        return ActivitySaleDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        Sale sale = (Sale) getIntent().getSerializableExtra(AppConstant.SALES);
        saleViewModel.setSale(sale);
    }

    @Override
    protected void initAction() {

    }
}