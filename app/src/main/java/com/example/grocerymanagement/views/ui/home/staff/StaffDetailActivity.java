package com.example.grocerymanagement.views.ui.home.staff;

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
import com.example.grocerymanagement.databinding.ActivityStaffDetailBinding;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;

public class StaffDetailActivity extends BaseActivity<ActivityStaffDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected ActivityStaffDetailBinding getViewBinding() {
        return ActivityStaffDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        Staff staff = (Staff) getIntent().getSerializableExtra(AppConstant.STAFFS);
        staffViewModel.setStaff(staff);
    }

    @Override
    protected void initAction() {
    }
}