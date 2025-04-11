package com.example.grocerymanagement.views.ui.home.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentClientAddBinding;
import com.example.grocerymanagement.views.ui.home.user.model.Client;

public class ClientAddFragment extends BaseFragment<FragmentClientAddBinding> {

    @Override
    protected FragmentClientAddBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentClientAddBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        binding.layoutHeader.navHeaderName.setText(R.string.text_add_client);
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnCancel.setOnClickListener(view -> popBackStack());

        binding.btnSave.setOnClickListener(view -> {
            String clientName = binding.editClientName.getText().toString();
            String clientAddress = binding.editClientAddress.getText().toString();
            String clientNumberPhone = binding.editClientPhone.getText().toString();
            String clientEmail = binding.editClientEmail.getText().toString();
            boolean isEnabled = binding.clientStatus.isChecked();
            addClientToFireStore(new Client("", clientName, clientAddress, clientNumberPhone, clientEmail, isEnabled));
        });
    }

    private void addClientToFireStore(Client client) {
        db.collection(AppConstant.CLIENTS).add(client).addOnSuccessListener(documentReference -> {
            client.setId(documentReference.getId());
            documentReference.set(client);
            showToast(getString(R.string.text_client_added_successfully));
            popBackStack();
        }).addOnFailureListener(e -> {
            showToast(getString(R.string.text_error_adding_client) + e);
        });
    }
}