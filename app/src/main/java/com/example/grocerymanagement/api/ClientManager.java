package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.user.model.Client;

public class ClientManager extends FirebaseHandler<Client> {
    public ClientManager() {
        super(AppConstant.CLIENTS);
    }
}
