package com.example.grocerymanagement.views.ui.home.staff.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerymanagement.views.ui.home.staff.model.Staff;

public class StaffViewModel extends ViewModel {
    MutableLiveData<Staff> staff = new MutableLiveData<>();
    MutableLiveData<String> positionName = new MutableLiveData<>();

    public MutableLiveData<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff.setValue(staff);
    }

    public MutableLiveData<String> getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName.setValue(positionName);
    }
}
