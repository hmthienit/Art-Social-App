package com.example.grocerymanagement.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.example.grocerymanagement.api.ApiService;
import com.example.grocerymanagement.api.RetrofitClient;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.helper.SharedPreferencesHelper;
import com.example.grocerymanagement.utils.DatePickerUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected T binding;
    protected SharedPreferencesHelper dataShared;
    protected DatePickerUtils datePickerUtils;
    protected FirebaseFirestore db;
    protected ApiService apiService;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();
        apiService = RetrofitClient.getClient(AppConstant.BASE_URL).create(ApiService.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getViewBinding(inflater, container);
        dataShared = new SharedPreferencesHelper(requireContext());
        datePickerUtils = new DatePickerUtils(requireContext());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initAction();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void navigateTo(int navDirections) {
        Navigation.findNavController(requireView()).navigate(navDirections);
    }

    protected void navigateTo(int navDirections, Bundle data) {
        Navigation.findNavController(requireView()).navigate(navDirections, data);
    }

    protected void popBackStack() {
        Navigation.findNavController(requireView()).popBackStack();
    }

    protected void backToPrevActivity(){
        requireActivity().finish();
    }

    protected void startActivity(Class<?> targetActivity) {
        Intent intent = new Intent(requireActivity(), targetActivity);
        startActivity(intent);
    }

    // Method to start an activity with data
    protected void startActivityWithData(Class<?> targetActivity, Bundle data) {
        Intent intent = new Intent(requireActivity(), targetActivity);
        intent.putExtras(data);
        startActivity(intent);
    }

    protected abstract T getViewBinding(LayoutInflater inflater, ViewGroup container); // Method to show a Toast message

    protected void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showView(View v) {
        v.setVisibility(View.VISIBLE);
    }

    protected void hideView(View v) {
        v.setVisibility(View.GONE);
    }

    protected void invisibleView(View v) {
        v.setVisibility(View.INVISIBLE);
    }

    public boolean getIsLoggedIn() {
        return !dataShared.getToken().isEmpty();
    }

    protected void closeActivity() {
        requireActivity().finish();
    }

    protected abstract void initViews();

    protected abstract void initAction();

    protected void showLoading(){
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity<?>) getActivity()).showLoading();
        }
    }

    protected void hideLoading(){
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity<?>) getActivity()).hideLoading();
        }
    }
}
