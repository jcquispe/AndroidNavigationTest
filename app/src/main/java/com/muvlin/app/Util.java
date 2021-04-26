package com.muvlin.app;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Util {

    public AlertDialog SimpleApertDialog(Context mContext, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
