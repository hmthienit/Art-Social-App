package com.example.grocerymanagement.views.ui.home.category.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String categoryName;
    private boolean isEnable;
    private String createdAt;

    public Category(){}


    public Category(String id, String categoryName, boolean isEnable, String createdAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.isEnable = isEnable;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
