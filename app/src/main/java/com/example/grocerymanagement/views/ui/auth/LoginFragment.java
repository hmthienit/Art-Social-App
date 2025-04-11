package com.example.grocerymanagement.views.ui.auth;

import android.os.Bundle;

import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.FirebaseAuthService;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentLoginBinding;
import com.example.grocerymanagement.views.ui.HomeActivity;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends BaseFragment<FragmentLoginBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentLoginBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        if (!BuildConfig.DEBUG){
            binding.loginEmailid.setText("admin@gmail.com");
            binding.loginPassword.setText("123456Aa");
        }
    }

    @Override
    protected void initAction() {
        binding.createAccount.setOnClickListener(view -> navigateTo(R.id.registerFragment));

        binding.loginBtn.setOnClickListener(view -> {
            if (!binding.loginEmailid.getText().toString().isEmpty() && !binding.loginPassword.getText().toString().isEmpty()) {
                showLoading();
                FirebaseAuthService firebaseAuthService = new FirebaseAuthService();
                firebaseAuthService.loginUser(binding.loginEmailid.getText().toString(), binding.loginPassword.getText().toString(), new FirebaseAuthService.OnAuthCompleteListener() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        hideLoading();
                        startActivity(HomeActivity.class);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        hideLoading();
                        showToast("Failed to login");
                    }
                });
            } else {
                showToast("Please input information!");
            }
        });
    }
}