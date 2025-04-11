package com.example.grocerymanagement.views.ui.home.order.model;

import com.example.grocerymanagement.views.ui.home.home.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    String id;
    String customerName;
    String phoneNumber;
    List<Product> listProd;
    String staffId;
    String createdAt;
    boolean isEnable;

    public Order() {
    }

    public Order(String id, String customerName, String phoneNumber, List<Product> listProd, String staffId, String createdAt, boolean isEnable) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.listProd = listProd;
        this.staffId = staffId;
        this.createdAt = createdAt;
        this.isEnable = isEnable;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Product> getListProd() {
        return listProd;
    }

    public void setListProd(List<Product> listProd) {
        this.listProd = listProd;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void removeProduct(String productId) {
        listProd.removeIf(product -> productId.equals(product.getId()));
    }
}
