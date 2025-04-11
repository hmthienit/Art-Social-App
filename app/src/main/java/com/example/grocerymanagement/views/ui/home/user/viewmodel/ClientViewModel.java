package com.example.grocerymanagement.views.ui.home.user.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerymanagement.views.ui.home.user.model.Client;

public class ClientViewModel extends ViewModel {
    MutableLiveData<Client> client = new MutableLiveData<>();

    public MutableLiveData<Client> getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client.setValue(client);
    }
}
