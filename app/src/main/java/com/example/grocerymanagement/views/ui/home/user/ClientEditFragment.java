package com.example.grocerymanagement.views.ui.home.user;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentClientEditBinding;
import com.example.grocerymanagement.views.ui.home.user.model.Client;
import com.example.grocerymanagement.views.ui.home.user.viewmodel.ClientViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientEditFragment extends BaseFragment<FragmentClientEditBinding> {

    private ClientViewModel clientViewModel;

    @Override
    protected FragmentClientEditBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentClientEditBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        Client client = clientViewModel.getClient().getValue();
        if (client != null) {
            binding.layoutHeader.navHeaderName.setText(client.getCustomerName());
            binding.editClientName.setText(client.getCustomerName());
            binding.editClientAddress.setText(client.getAddress());
            binding.editClientEmail.setText(client.getEmail());
            binding.editClientPhone.setText(client.getPhoneNumber());
            binding.clientStatus.setChecked(client.isEnabled());
        }
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnCancel.setOnClickListener(view -> popBackStack());
        binding.btnSave.setOnClickListener(view -> {
            Map<String, Object> client = new HashMap<>();
            client.put(AppConstant.CLIENT_NAME, binding.editClientName.getText().toString());
            client.put(AppConstant.CLIENT_ADDRESS, binding.editClientAddress.getText().toString());
            client.put(AppConstant.CLIENT_EMAIL, binding.editClientEmail.getText().toString());
            client.put(AppConstant.CLIENT_ENABLED, binding.clientStatus.isChecked());
            client.put(AppConstant.CLIENT_PHONE_NUMBER, binding.editClientPhone.getText().toString());

            editClient(Objects.requireNonNull(clientViewModel.getClient().getValue()).getId(), client);
        });
    }

    public void editClient(String clientID, Map<String, Object> client) {

        db.collection(AppConstant.CLIENTS).document(String.valueOf(clientID)).update(client).addOnSuccessListener(aVoid -> {
            showToast(getString(R.string.text_client_added_successfully));
            backToPrevActivity();
        }).addOnFailureListener(e -> {
            showToast(getString(R.string.text_failed_to_update_client));
        });

    }
}