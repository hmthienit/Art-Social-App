package com.example.grocerymanagement.base;

import java.io.Serializable;

public class BaseModel implements Serializable {
    private final int id;

    public BaseModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
