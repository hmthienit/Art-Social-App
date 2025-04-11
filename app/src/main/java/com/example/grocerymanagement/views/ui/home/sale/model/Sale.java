package com.example.grocerymanagement.views.ui.home.sale.model;

import java.io.Serializable;

public class Sale implements Serializable {
    private String id;
    private String saleName;
    private boolean status;
    private String createdAt;
    private String saleStartDate;
    private String saleEndDate;
    private String barCodeSale;
    private int saleValue;

    public Sale(){}

    public Sale(String id, String saleName, boolean status, String createdAt, String saleStartDate, String saleEndDate, String barCodeSale, int saleValue) {
        this.id = id;
        this.saleName = saleName;
        this.status = status;
        this.createdAt = createdAt;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
        this.barCodeSale = barCodeSale;
        this.saleValue = saleValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSaleStartDate() {
        return saleStartDate;
    }

    public void setSaleStartDate(String saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public String getSaleEndDate() {
        return saleEndDate;
    }

    public void setSaleEndDate(String saleEndDate) {
        this.saleEndDate = saleEndDate;
    }

    public String getBarCodeSale() {
        return barCodeSale;
    }

    public void setBarCodeSale(String barCodeSale) {
        this.barCodeSale = barCodeSale;
    }

    public int getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(int saleValue) {
        this.saleValue = saleValue;
    }
}
