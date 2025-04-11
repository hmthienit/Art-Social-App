package com.example.grocerymanagement.views.ui.home.order;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.api.ProductManager;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.ActivityOrderScannerBinding;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.home.viewmodel.ProductViewModel;
import com.example.grocerymanagement.views.ui.home.order.model.Order;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderScannerActivity extends BaseActivity<ActivityOrderScannerBinding> {

    private CameraSource cameraSource;
    private final int requestCode = 1001;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected ActivityOrderScannerBinding getViewBinding() {
        return ActivityOrderScannerBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        orderViewModel.setOrder((Order) getIntent().getSerializableExtra(AppConstant.ORDERS));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupBarcodeScanner();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestCode);
        }
    }

    @Override
    protected void initAction() {

    }


    private void setupBarcodeScanner() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector).setAutoFocusEnabled(true).build();
        binding.cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(binding.cameraPreview.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    if (!isChecked) {
                        isChecked = true;
                        Log.e("SAASAS", barcodes.valueAt(0).displayValue + " hdhdhhdhdhdh");
                        getProdById(barcodes.valueAt(0).displayValue);
                    }

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupBarcodeScanner();
            } else {
                showToast("Failed");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void addToListProductOrder(String id) {
        OrderManager orderManager = new OrderManager();
        Map<String, Object> product = new HashMap<>();
        Objects.requireNonNull(productViewModel.getProduct().getValue()).setQuantity(1);
        product.put(AppConstant.ORDER_LIST_PRDD, FieldValue.arrayUnion(productViewModel.getProduct().getValue()));
        if (orderViewModel.getOrder().getValue() != null) {
            orderManager.updateDocument(orderViewModel.getOrder().getValue().getId(), product, unused -> {
//                showToast("Update success " + product);
                finish();
            }, e -> {
            });
        }
    }

    private void getProdById(String id) {
        ProductManager productManager = new ProductManager();
        productManager.getProductByBarcode(AppConstant.PRODUCT, id, documentSnapshot -> {
            for (QueryDocumentSnapshot document : documentSnapshot) {
                Product product = document.toObject(Product.class);
                Log.d("Firestore", "Product: " + product.getName() + ", Price: " + product.getPrice());
                productViewModel.setProduct(product);
            }
//            addToListProductOrder(id);
            addOrUpdateProductInOrder(Objects.requireNonNull(orderViewModel.getOrder().getValue()).getId(), productViewModel.getProduct().getValue());
        }, e -> {
            showToast("Failed to get product" + id);
        });
    }

    private void addOrUpdateProductInOrder(String orderID, Product newProduct) {
        OrderManager orderManager = new OrderManager();
        orderManager.addOrUpdateProductInOrder(AppConstant.ORDERS, orderID, newProduct,
                aVoid -> {
                    finish();
                    Log.d("Firestore", "Product successfully added or updated in prodList!");
                },
                e -> Log.w("Firestore", "Error adding or updating product in prodList", e));
    }


}