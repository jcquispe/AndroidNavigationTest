package com.muvlin.app.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Alert {

    public void simpleAlert(Context context, String titulo, String mensaje, String boton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton(boton, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
