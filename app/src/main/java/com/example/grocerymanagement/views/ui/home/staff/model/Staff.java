package com.example.grocerymanagement.views.ui.home.staff.model;

import com.example.grocerymanagement.views.ui.auth.model.User;

import java.io.Serializable;

public class Staff implements Serializable {
    private String id;
    private String staffName;
    private String staffPosition;
    private String staffCodeNumber;
    private String staffAccount;
    private boolean isEnable;
    private String createAt;
    private String avt;

    public Staff() {
    }

    public Staff(String id, String staffName, String staffPosition, String staffCodeNumber, String staffAccount, boolean isEnable, String createAt, String avt) {
        this.id = id;
        this.staffName = staffName;
        this.staffPosition = staffPosition;
        this.staffCodeNumber = staffCodeNumber;
        this.staffAccount = staffAccount;
        this.isEnable = isEnable;
        this.createAt = createAt;
        this.avt = avt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPosition() {
        return staffPosition;
    }

    public void setStaffPosition(String staffPosition) {
        this.staffPosition = staffPosition;
    }

    public String getStaffCodeNumber() {
        return staffCodeNumber;
    }

    public void setStaffCodeNumber(String staffCodeNumber) {
        this.staffCodeNumber = staffCodeNumber;
    }

    public String getStaffAccount() {
        return staffAccount;
    }

    public void setStaffAccount(String staffAccount) {
        this.staffAccount = staffAccount;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }
}
