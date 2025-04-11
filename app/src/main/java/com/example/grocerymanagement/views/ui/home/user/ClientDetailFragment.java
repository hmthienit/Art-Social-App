package com.example.grocerymanagement.views.ui.home.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.ClientManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentClientDetailBinding;
import com.example.grocerymanagement.helper.DialogHelper;
import com.example.grocerymanagement.views.ui.home.user.model.Client;
import com.example.grocerymanagement.views.ui.home.user.viewmodel.ClientViewModel;

import java.util.Objects;


public class ClientDetailFragment extends BaseFragment<FragmentClientDetailBinding> {

    private ClientViewModel clientViewModel;


    @Override
    protected FragmentClientDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentClientDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);

        Client client = clientViewModel.getClient().getValue();
        if (client != null) {
            binding.layoutHeader.navHeaderName.setText(client.getCustomerName());
            binding.clientId.setText("ID: " + client.getId());
            binding.clientName.setText("Name: " + client.getCustomerName());
            binding.clientEmail.setText("Email: " + client.getEmail());
            binding.clientPhone.setText("Phone: " + client.getPhoneNumber());
            binding.clientAddress.setText("Address: " + client.getAddress());
        }
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> closeActivity());
        binding.btnEdit.setOnClickListener(view -> navigateTo(R.id.clientEditFragment));
        binding.btnDelete.setOnClickListener(view -> {
            ClientManager clientManager = new ClientManager();
            DialogHelper.showConfirmationDialog(
                    requireContext(),
                    getString(R.string.text_delete),
                    getString(R.string.text_do_you_want_to_delete_the) + Objects.requireNonNull(clientViewModel.getClient().getValue()).getCustomerName() + "?",
                    getString(R.string.text_yes),
                    (dialog, which) -> {
                        clientManager.deleteDocument(clientViewModel.getClient().getValue().getId(), result -> {
                                    showToast("Delete the " + clientViewModel.getClient().getValue().getCustomerName() + " successfully!");
                                    requireActivity().finish();
                                },
                                e -> showToast("Error while delete the " + clientViewModel.getClient().getValue().getCustomerName()));
                    },
                    getString(R.string.text_no),
                    ((dialogInterface, i) -> popBackStack())
            );
        });
    }
}