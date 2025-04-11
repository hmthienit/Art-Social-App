package com.example.grocerymanagement.views.ui.auth.model;

public class User {
    private String id;
    private String userName;
    private String email;
    private String fullName;
    private String password;
    private String address;
    private String role;

    public User(String id, String userName, String email, String fullName, String password, String address, String role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
