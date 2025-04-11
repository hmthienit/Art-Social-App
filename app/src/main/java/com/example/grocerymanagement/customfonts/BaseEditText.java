package com.example.grocerymanagement.customfonts;

import static android.view.Gravity.CENTER_VERTICAL;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.example.grocerymanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class BaseEditText extends TextInputLayout {
    public BaseEditText(Context context) {
        super(context);
        init(context, null);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TextInputEditText editText = new TextInputEditText(context);
        editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        editText.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        editText.setPadding(10, 10, 10, 10);
        editText.setTextColor(ContextCompat.getColor(context, R.color.black));
        editText.setHintTextColor(ContextCompat.getColor(context, R.color.yellow));
        editText.setTextSize(16f);
        editText.setSingleLine(true);
        editText.setGravity(CENTER_VERTICAL);
        this.addView(editText);
    }

    public void setHintText(String hint) {
        this.setHint(hint);
    }

    public void setInputType(int type) {
        this.getEditText().setInputType(type);
    }
}