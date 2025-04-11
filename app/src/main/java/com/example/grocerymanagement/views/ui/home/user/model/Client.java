package com.example.grocerymanagement.views.ui.home.user.model;

import java.io.Serializable;

public class Client implements Serializable {
    private String id;
    private String customerName;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean isEnabled;

    public Client(){}

    public Client(String id, String customerName, String address, String phoneNumber, String email, boolean isEnabled) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isEnabled = isEnabled;
    }

    public Client(String id, String customerName, String address, String phoneNumber, boolean isEnabled) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isEnabled = isEnabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
