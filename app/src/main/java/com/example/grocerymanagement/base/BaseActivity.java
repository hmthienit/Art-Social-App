package com.example.grocerymanagement.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.grocerymanagement.helper.SharedPreferencesHelper;
import com.example.grocerymanagement.utils.DatePickerUtils;
import com.example.grocerymanagement.views.ui.home.category.viewmodel.CategoryViewModel;
import com.example.grocerymanagement.views.ui.home.home.viewmodel.ProductViewModel;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;
import com.example.grocerymanagement.views.ui.home.sale.viewmodel.SaleViewModel;
import com.example.grocerymanagement.views.ui.home.staff.viewmodel.StaffViewModel;
import com.example.grocerymanagement.views.ui.home.user.viewmodel.ClientViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T binding;


    private Boolean isLoggedIn;
    protected SharedPreferencesHelper dataShared;
    protected DatePickerUtils datePickerUtils;
    protected FirebaseFirestore db;
    protected ProductViewModel productViewModel;
    protected CategoryViewModel categoryViewModel;
    protected SaleViewModel saleViewModel;
    protected ClientViewModel clientViewModel;
    protected StaffViewModel staffViewModel;
    protected OrderViewModel orderViewModel;

    private ProgressBar progressBar;
    private View overlay;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        overlay = new View(this);// Semi-transparent black
        overlay.setVisibility(View.GONE); // Initially hidden
        frameLayout.addView(overlay, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Create and add the ProgressBar
        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.GONE); // Initially hidden
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER; // Center the ProgressBar
        frameLayout.addView(progressBar, params);

        // Set content view
        setContentView(frameLayout);
        frameLayout.addView(binding.getRoot());


//        setContentView(binding.getRoot());
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // ViewModel init
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        staffViewModel = new ViewModelProvider(this).get(StaffViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Utils init
        dataShared = new SharedPreferencesHelper(this);
        datePickerUtils = new DatePickerUtils(this);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        initViews();
        initAction();
    }

    // Abstract method to initialize View Binding
    protected abstract T getViewBinding();

    // Method to show a Toast message
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to start an activity without data
    protected void startActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    // Method to start an activity with data
    protected void startActivityWithData(Class<?> targetActivity, Bundle data) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtras(data);
        startActivity(intent);
    }

    protected void showView(View v) {
        v.setVisibility(View.VISIBLE);
    }

    protected void hideView(View v) {
        v.setVisibility(View.GONE);
    }

    protected void invisibleView(View v) {
        v.setVisibility(View.INVISIBLE);
    }

    public boolean getIsLoggedIn() {
        return !dataShared.getToken().isEmpty();
    }

    // Method to get data from intent
    protected Bundle getIntentData() {
        return getIntent().getExtras();
    }

    protected abstract void initViews();

    protected abstract void initAction();

    // Method to show the loading indicator
    public void showLoading() {
        overlay.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    // Method to hide the loading indicator
    public void hideLoading() {
        overlay.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

}
