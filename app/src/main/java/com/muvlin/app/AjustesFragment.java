package com.muvlin.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjustesFragment extends Fragment {

    private EditText editTextMargen;
    private Button buttonActualizar;

    public static final String MyPREFERENCES = "MySettings" ;
    public static final String Margen = "margenKey";
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);
        editTextMargen = view.findViewById(R.id.editTextMargen);
        buttonActualizar = view.findViewById(R.id.buttonActualizar);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (getMargen() == 0) {
            saveDefaultMargen();
        }

        editTextMargen.setText(String.valueOf(getMargen()));

        buttonActualizar.setOnClickListener(v -> {
            String margenText = editTextMargen.getText().toString().trim();
            if (margenText.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("ATENCIÓN");
                builder.setMessage("El márgen de cotización no puede estar vacío");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                saveSettings(Float.parseFloat(margenText));
            }
        });
        return view;
    }

    private void saveSettings(Float margen) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(Margen, margen);
        editor.commit();
        Toast.makeText(getContext(),"Márgen actualizado a " + margen,Toast.LENGTH_LONG).show();
        moveToCotizacion();
    }

    private void saveDefaultMargen() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(Margen, 25);
        editor.commit();
    }

    private Float getMargen() {
        return sharedpreferences.getFloat(Margen, 0);
    }

    private void moveToCotizacion() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, CotizacionFragment.class, null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}