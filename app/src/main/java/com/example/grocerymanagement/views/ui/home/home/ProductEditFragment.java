package com.example.grocerymanagement.views.ui.home.home;

import android.annotation.SuppressLint;
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
import androidx.navigation.Navigation;

import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentProductEditBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.category.adpater.CategorySpinnerAdapter;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.example.grocerymanagement.views.ui.home.category.viewmodel.CategoryViewModel;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.home.viewmodel.ProductViewModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductEditFragment extends BaseFragment<FragmentProductEditBinding> {

    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;
    private SaleViewModel saleViewModel;
    List<Category> listCate;
    List<Sale> listSale;
    private String imagePath = "";
    private String productCategory;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Bitmap bitmap;

    @Override
    protected FragmentProductEditBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProductEditBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews() {
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        saleViewModel = new ViewModelProvider(requireActivity()).get(SaleViewModel.class);

        Product productDetailActivity = productViewModel.getProduct().getValue();
        ImageUtils.getInstance(requireContext()).setImage(binding.productImage, AppConstant.BASE_URL + productDetailActivity.getImage());
        binding.layoutHeader.navHeaderName.setText(productDetailActivity.getName());
        binding.edtProductName.setText(productDetailActivity.getName());
        binding.edtProductDescription.setText(productDetailActivity.getDescription());
        binding.edtProductPrice.setText(productDetailActivity.getPrice() + "");
        binding.edtProductQuantity.setText(productDetailActivity.getQuantity() + "");
        binding.edtProductBarcode.setText(productDetailActivity.getBarCode() + "");
        binding.edtProductWeight.setText(productDetailActivity.getWeight() + "");
        imagePath = productDetailActivity.getImage();
        productCategory = productDetailActivity.getCategory();
        ImageUtils.getInstance(requireContext()).setImage(binding.productImage, AppConstant.BASE_URL + productDetailActivity.getImage());


        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                showView(binding.productImage);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    binding.productImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListCategory();
        getListSale();
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                categoryViewModel.setCategory(selectedCategory);
                showToast("Selected: " + Objects.requireNonNull(categoryViewModel.getCategory().getValue()).getCategoryName());
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

        binding.productImage.setOnClickListener(view -> ImageUtils.getInstance(requireContext()).openImagePicker(imagePickerLauncher));
        binding.btnSave.setOnClickListener(view -> {
            if (bitmap != null) {
                uploadImage();
            } else {
                Map<String, Object> product = new HashMap<>();
                product.put(AppConstant.PROD_NAME, binding.edtProductName.getText().toString());
                product.put(AppConstant.PROD_PRICE, Integer.parseInt(binding.edtProductPrice.getText().toString()));
                product.put(AppConstant.PROD_DESCRIPTION, binding.edtProductDescription.getText().toString());
                product.put(AppConstant.PROD_QUANTITY, Integer.parseInt(binding.edtProductQuantity.getText().toString()));
                product.put(AppConstant.PROD_CATEGORY, productCategory);
                product.put(AppConstant.PROD_BARCODE, binding.edtProductBarcode.getText().toString());
                product.put(AppConstant.PROD_WEIGHT, Integer.parseInt(binding.edtProductQuantity.getText().toString()));
                product.put(AppConstant.PROD_IMAGE, imagePath);
                product.put(AppConstant.PROD_SALE, saleViewModel.getSale().getValue());
                editProduct(Objects.requireNonNull(productViewModel.getProduct().getValue()).getId(), product);
            }
        });

        binding.spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category selectedCategory = (Category) adapterView.getItemAtPosition(i);
                productCategory = selectedCategory.getCategoryName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListCategory() {
        listCate = new ArrayList<>();
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

    public void editProduct(String productID, Map<String, Object> product) {

        db.collection(AppConstant.PRODUCT).document(String.valueOf(productID)).update(product).addOnSuccessListener(aVoid -> {
            showToast("Product updated successfully");
            backToPrevActivity();
        }).addOnFailureListener(e -> {
            showToast("Failed to update product");
        });

    }

    private void uploadImage() { // Convert bitmap to file
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
                            imagePath = responseObject.getString("path");
                            showToast("Image uploaded successfully! ID: " + imagePath);

                            Map<String, Object> product = new HashMap<>();
                            product.put(AppConstant.PROD_NAME, binding.edtProductName.getText().toString());
                            product.put(AppConstant.PROD_PRICE, Integer.parseInt(binding.edtProductPrice.getText().toString()));
                            product.put(AppConstant.PROD_DESCRIPTION, binding.edtProductDescription.getText().toString());
                            product.put(AppConstant.PROD_QUANTITY, Integer.parseInt(binding.edtProductQuantity.getText().toString()));
                            product.put(AppConstant.CATEGORIES, productCategory);
                            product.put(AppConstant.PROD_BARCODE, binding.edtProductBarcode.getText().toString());
                            product.put(AppConstant.PROD_WEIGHT, Integer.parseInt(binding.edtProductQuantity.getText().toString()));
                            product.put(AppConstant.PROD_IMAGE, imagePath);
                            product.put(AppConstant.PROD_SALE, saleViewModel.getSale().getValue());
                            editProduct(Objects.requireNonNull(productViewModel.getProduct().getValue()).getId(), product);
                        } else {
                            String errorMessage = responseObject.getString("message");
                            showToast("Upload failed: " + errorMessage);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Request failed: " + t.getMessage());
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