package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;

public class StaffManager extends FirebaseHandler<Staff>{
    public StaffManager() {
        super(AppConstant.STAFFS);
    }
}
