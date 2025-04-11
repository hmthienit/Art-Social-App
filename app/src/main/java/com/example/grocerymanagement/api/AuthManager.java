package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.auth.model.User;

public class AuthManager extends FirebaseHandler<User> {
    public AuthManager() {
        super(AppConstant.USERS);
    }
}
