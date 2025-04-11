package com.example.grocerymanagement.utils;

import android.annotation.SuppressLint;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtils {

    @SuppressLint("WrongConstant")
    public static void initAdapter(
            RecyclerView.Adapter<?> mAdapter,
            RecyclerView rev,
            int orientation,
            int spanCount,
            boolean isSetScrollMiddle
    ) {
        if (orientation == 0) {
            orientation = LinearLayoutManager.VERTICAL;
        } else {
            orientation = LinearLayoutManager.HORIZONTAL;
        }

        if (spanCount == 0) {
            spanCount = 1;
        }

        rev.setAdapter(mAdapter);
        rev.setLayoutManager(new GridLayoutManager(rev.getContext(), spanCount, orientation, false));
        rev.setNestedScrollingEnabled(false);
        rev.setHasFixedSize(false);

        if (orientation == LinearLayoutManager.HORIZONTAL && isSetScrollMiddle) {
            onScrolledToMiddlePosition(rev);
        }
    }

    public static void onScrolledToMiddlePosition(RecyclerView rev) {
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        if (rev.getOnFlingListener() == null) {
            snapHelper.attachToRecyclerView(rev);
        }
        rev.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Assuming the first child is always present
                if (recyclerView.getChildCount() > 0) {
                    ViewCompat.requireViewById(recyclerView, 0);
                }
            }
        });
    }
}

