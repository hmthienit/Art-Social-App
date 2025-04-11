package com.example.grocerymanagement.views.ui.auth;

import android.os.Bundle;

import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.api.AuthManager;
import com.example.grocerymanagement.api.FirebaseAuthService;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentRegisterBinding;
import com.example.grocerymanagement.views.ui.auth.model.User;
import com.example.grocerymanagement.views.ui.auth.viewmodel.AuthViewModel;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {

    private AuthViewModel authViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentRegisterBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

    }

    @Override
    protected void initAction() {
        binding.signUpBtn.setOnClickListener(view -> {
            FirebaseAuthService firebaseAuthService = new FirebaseAuthService();
            firebaseAuthService.registerUser(binding.userEmailId.getText().toString(),
                    binding.password.getText().toString(),
                    new FirebaseAuthService.OnAuthCompleteListener() {
                        @Override
                        public void onSuccess(FirebaseUser user) {
                            showToast("Register success!");
                            User authUser = new User(
                                    "",
                                    "",
                                    binding.userEmailId.getText().toString(),
                                    binding.fullName.getText().toString(),
                                    binding.password.getText().toString(),
                                    "",
                                    authViewModel.getRole().getValue());
                            addUser(authUser);
                            popBackStack();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            showToast("Failed while registration");
                        }
                    });
        });

        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String role = (String) parent.getItemAtPosition(position);
                authViewModel.setRole(role);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addUser(User user) {
        AuthManager userManager = new AuthManager();
        userManager.addDocument(user, documentReference -> {
            user.setId(documentReference.getId());
            documentReference.set(user);
            Log.d("RegisterFragment", "Add User Successfully!");
        }, e -> {
            Log.e("RegisterFragment", "Failed When Add User");
        });
    }
}