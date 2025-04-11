package com.example.grocerymanagement.views.ui.home.social_post.model;

public class TestImage {
    private String id;
    private String imageUrl;
    private String createdAt;

    public TestImage() {
    }

    public TestImage(String id, String imageUrl, String createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
