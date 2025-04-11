package com.example.grocerymanagement.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.order.model.Order;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public abstract class FirebaseHandler<T> {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference;

    public FirebaseHandler(String collectionPath) {
        this.collectionReference = db.collection(collectionPath);
    }

    public void addDocument(T document, OnSuccessListener<DocumentReference> successListener, OnFailureListener failureListener) {
        collectionReference.add(document).addOnSuccessListener(successListener).addOnFailureListener(failureListener);
    }

    public void updateDocument(String documentId, Map<String, Object> updates, OnSuccessListener<Void> successListener, OnFailureListener failureListener) {
        collectionReference.document(documentId).update(updates)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public void deleteDocument(String documentId, OnSuccessListener<Void> successListener, OnFailureListener failureListener) {
        collectionReference.document(documentId).delete()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public void getDocument(String documentId, Class<T> clazz, OnSuccessListener<DocumentSnapshot> successListener, OnFailureListener failureListener) {
        collectionReference.document(documentId).get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public void getAllDocuments(Class<T> clazz, OnSuccessListener<QuerySnapshot> successListener, OnFailureListener failureListener) {
        collectionReference.get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public void getProductByBarcode(String collection, String barcode, OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection(collection).whereEqualTo(AppConstant.PROD_BARCODE, barcode).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    onSuccessListener.onSuccess(task.getResult());
                } else {
                    onFailureListener.onFailure(task.getException());
                }
            }
        });
    }

    public void addOrUpdateProductInOrder(String collection, String documentId, Product newProduct, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection(collection).document(documentId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                List<Product> prodList = document.toObject(Order.class).getListProd();
                boolean productExists = false;
                for (Product product : prodList) {
                    if (product.getId().equals(newProduct.getId())) {
                        product.setQuantity(product.getQuantity() + 1);
                        productExists = true;
                        break;
                    }
                }
                if (!productExists) {
                    newProduct.setQuantity(1);
                    prodList.add(newProduct);
                }
                db.collection(collection).document(documentId).update(AppConstant.ORDER_LIST_PRDD, prodList).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
            } else {
                onFailureListener.onFailure(task.getException());
            }
        });
    }

    public void removeItemFromListProdInOrder(String orderId, String productId, OrderViewModel orderViewModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AppConstant.ORDERS).document(orderId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot documentSnapshot = task.getResult();
                Order order = documentSnapshot.toObject(Order.class);
                if (order != null) {
                    order.removeProduct(productId);
                    db.collection(AppConstant.ORDERS).document(orderId).set(order).addOnSuccessListener(aVoid -> {
                        orderViewModel.setOrder(order);
                        long totalPrice = 0;
                        for (Product prodItem : order.getListProd()) {
                            totalPrice += prodItem.getSale().getSaleValue() == 0 ? (long) prodItem.getPrice() * prodItem.getQuantity() : (prodItem.getPrice() - ((prodItem.getPrice() * (prodItem.getSale().getSaleValue())) / 100)) * prodItem.getQuantity();
                        }
                        orderViewModel.setTotalPrice(totalPrice);
                        Log.d("Firestore", "Item successfully removed from listProd");
                    }).addOnFailureListener(e -> {
                        Log.w("Firestore", "Error removing item from listProd", e);
                    });
                }
            } else {
                Log.w("Firestore", "Error getting order document", task.getException());
            }
        });
    }
}
