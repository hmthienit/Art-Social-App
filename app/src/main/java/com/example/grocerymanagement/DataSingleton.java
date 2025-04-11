package com.example.grocerymanagement;



public class DataSingleton {
    private static DataSingleton instance;
//    private Product product;

    private DataSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }
//
//
//    public void setProductDetail(Product productDetail) {
//        this.product = productDetail;
//    }
//
//    public Product getProductDetail() {
//        return product;
//    }
}
