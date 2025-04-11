package com.example.grocerymanagement.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.example.grocerymanagement.views.ui.home.home.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseAdapter<T, VB extends ViewBinding> extends RecyclerView.Adapter<BaseAdapter.ViewHolder<VB>> {

    private AsyncListDiffer<T> differ;
    private OnItemClickListener onItemClickListener;

    public BaseAdapter(DiffUtil.ItemCallback<T> diffCallback) {
        differ = new AsyncListDiffer<>(this, diffCallback);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VB binding = createBinding(LayoutInflater.from(parent.getContext()), parent);
        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<VB> holder, int position) {
        T item = differ.getCurrentList().get(position);
        bind(holder.binding, item, position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<T> list) {
        differ.submitList(list);
    }

    public abstract VB createBinding(LayoutInflater inflater, ViewGroup parent);

    public abstract void bind(@NonNull VB binding, T item, int position);

    public static class ViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {
        public final VB binding;

        public ViewHolder(@NonNull VB binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void removeItem(int position) {
        List<Product> currentList = (List<Product>) new ArrayList<>(differ.getCurrentList());
        if (position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            submitList((List<T>) currentList);
        }
    }
}



