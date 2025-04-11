package com.example.grocerymanagement.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import java.util.List;

public abstract class BaseSpinnerAdapter<T, VB extends ViewBinding> extends ArrayAdapter<T> {
    private final Context context;
    private final int resource;

    public BaseSpinnerAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        VB binding;
        if (convertView == null) {
            binding = createBinding(LayoutInflater.from(context), parent);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (VB) convertView.getTag();
        }
        T item = getItem(position);
        bind(binding, item);
        return convertView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        VB binding;
        if (convertView == null) {
            binding = createBinding(LayoutInflater.from(context), parent);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (VB) convertView.getTag();
        }
        T item = getItem(position);
        bind(binding, item);
        return convertView;
    }

    protected abstract VB createBinding(LayoutInflater inflater, ViewGroup parent);

    protected abstract void bind(VB binding, T item);
}