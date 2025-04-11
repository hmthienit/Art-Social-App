package com.example.grocerymanagement.views.ui.home.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentProductAddBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.category.adpater.CategorySpinnerAdapter;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.example.grocerymanagement.views.ui.home.category.viewmodel.CategoryViewModel;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.sale.adapter.SaleSpinnerAdapter;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;
import com.example.grocerymanagement.views.ui.home.sale.viewmodel.SaleViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAddFragment extends BaseFragment<FragmentProductAddBinding> {

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Bitmap bitmap;
    private CategoryViewModel categoryViewModel;
    private SaleViewModel saleViewModel;
    List<Category> listCate;
    List<Sale> listSale;

    @Override
    protected FragmentProductAddBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProductAddBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

        binding.layoutHeader.navHeaderName.setText(R.string.text_product_add);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                showView(binding.imgProduct);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    binding.imgProduct.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (selectedImageUri != null)
                    binding.editProductImage.setText(selectedImageUri.getPath());
            }
        });

        listCate = new ArrayList<>();

        getListCategory();
        getListSale();

    }

    @Override
    protected void initAction() {

        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                categoryViewModel.setCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spnSale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sale sale = (Sale) parent.getItemAtPosition(position);
                saleViewModel.setSale(sale);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.btnSave.setOnClickListener(view -> {

            if (bitmap != null) {
                uploadImage();
            } else {
                showToast("No image selected");
            }

        });
        binding.btnCancel.setOnClickListener(view -> popBackStack());
        binding.editProductImage.setOnClickListener(view -> ImageUtils.getInstance(requireContext()).openImagePicker(imagePickerLauncher));
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
    }


    private void addProductToFireStore(Product product) {
        db.collection(AppConstant.PRODUCT).add(product).addOnSuccessListener(documentReference -> {
            product.setId(documentReference.getId());
            documentReference.set(product);
            showToast(getString(R.string.text_product_added_successfully));
            popBackStack();
        }).addOnFailureListener(e -> {
            showToast(getString(R.string.text_error_adding_product) + e);
        });
    }


    private void uploadImage() {
        File file = new File(requireActivity().getCacheDir(), "image.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } // Create request body
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile); // Upload image
        Call<ResponseBody> call = apiService.uploadImage(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try { // Parse the response body as JSON
                        String responseBodyString = response.body().string();
                        JSONObject responseObject = new JSONObject(responseBodyString);
                        if (responseObject.getString("status").equals("success")) {
                            String imagePath = responseObject.getString("path");
                            showToast(getString(R.string.text_image_uploaded_successfully_id) + imagePath);

                            String productName = binding.editProductName.getText().toString();
                            String productDescription = binding.editProductDescription.getText().toString();
                            int productPrice = Integer.parseInt(binding.editProductPrice.getText().toString());
                            int productQuantity = Integer.parseInt(binding.editProductQuantity.getText().toString());
                            String productBarCode = binding.editProductBarcode.getText().toString();
                            String productImage = binding.editProductImage.getText().toString();
                            double productWeight = Double.parseDouble(binding.editProductWeight.getText().toString());
                            Product product = new Product("", productName, productDescription, productPrice, productQuantity, productBarCode, imagePath, new Date().toString(), productWeight, categoryViewModel.getCategory().getValue() == null ? listCate.get(0).getCategoryName() : categoryViewModel.getCategory().getValue().getCategoryName(), saleViewModel.getSale().getValue());
                            addProductToFireStore(product);


                        } else {
                            String errorMessage = responseObject.getString("message");
                            showToast(getString(R.string.text_upload_failed) + errorMessage);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast(getString(R.string.text_request_failed) + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast(getString(R.string.text_request_failed) + t.getMessage());
            }
        });
    }

    private void getListCategory() {
        if (db != null)
            db.collection(AppConstant.CATEGORIES).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Category category = document.toObject(Category.class);
                        if (category.getIsEnable())
                            listCate.add(category); // Display product data
                        Log.e("AAA", "Name: " + category.getCategoryName() + "\n");
                    }
                    categoryViewModel.setCategory(listCate.get(0));
                    CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(requireActivity(), listCate);
                    binding.spnCategory.setAdapter(adapter);
                } else {
                    Log.e("AAA", "Error getting documents: ", task.getException());
                }
            });
    }

    private void getListSale() {
        listSale = new ArrayList<>();
        if (db != null)
            db.collection(AppConstant.SALES).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Sale sale = document.toObject(Sale.class);
                        if (sale.getStatus())
                            listSale.add(sale);
                    }
                    saleViewModel.setSale(listSale.get(0));
                    SaleSpinnerAdapter saleSpinnerAdapter = new SaleSpinnerAdapter(requireContext(), listSale);
                    binding.spnSale.setAdapter(saleSpinnerAdapter);
                } else {
                    Log.e("AAA", "Error getting documents: ", task.getException());
                }
            });
    }


}