package com.example.grocerymanagement.views.ui.home.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentUserBinding;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.sale.SaleDetailActivity;
import com.example.grocerymanagement.views.ui.home.user.adapter.ClientAdapter;
import com.example.grocerymanagement.views.ui.home.user.model.Client;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment<FragmentUserBinding> {

    private UserViewModel mViewModel;
    private ClientAdapter clientAdapter;
    private ArrayList<Client> listClient;

    @Override
    protected FragmentUserBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentUserBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        binding.layoutHeader.navHeaderName.setText(R.string.text_client_management);
        clientAdapter = new ClientAdapter(requireContext());
        RecyclerViewUtils.initAdapter(clientAdapter, binding.revUser, 0, 1, false);
        List<Client> listClient = new ArrayList<>();

        clientAdapter.submitList(listClient);
        clientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getListClient();
    }

    @Override
    protected void initAction() {
        clientAdapter.setOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.CLIENTS, listClient.get(position));
            startActivityWithData(UserDetailActivity.class, bundle);
        });
        binding.layoutHeader.tvAdd.setOnClickListener(view -> navigateTo(R.id.clientAddFragment));
    }

    private void getListClient() {
        showLoading();
        listClient = new ArrayList<>();
        if (db != null)
            db.collection(AppConstant.CLIENTS).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Client client = document.toObject(Client.class);
                        listClient.add(client); // Display product data
                    }
                    clientAdapter.submitList(listClient);
                    clientAdapter.notifyDataSetChanged();
                } else {
                    Log.e("AAA", "Error getting documents: ", task.getException());
                }
                hideLoading();
            });
    }

}