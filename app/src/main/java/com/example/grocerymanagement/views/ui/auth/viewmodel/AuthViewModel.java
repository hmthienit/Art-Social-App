package com.example.grocerymanagement.views.ui.auth.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerymanagement.views.ui.auth.model.User;

public class AuthViewModel extends ViewModel {
    MutableLiveData<User> user = new MutableLiveData<>();

    MutableLiveData<String> role = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public MutableLiveData<String> getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role.setValue(role);
    }
}
