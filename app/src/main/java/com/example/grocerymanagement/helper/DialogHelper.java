package com.example.grocerymanagement.helper;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogHelper {

    public static void showConfirmationDialog(Context context, String title, String message,
                                              String positiveButtonText, DialogInterface.OnClickListener positiveAction,
                                              String negativeButtonText, DialogInterface.OnClickListener negativeAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButtonText, positiveAction);
        builder.setNegativeButton(negativeButtonText, negativeAction);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
