package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.database.model.Producto;
import com.google.android.material.navigation.NavigationView;
import com.nambimobile.widgets.efab.FabOption;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CotizacionFragment extends Fragment {

    private FabOption optManual, optListado, optGenerar;
    private ProductoViewModel mProductoViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_cotizacion, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewCotizacion);
        final ProductoListAdapter adapter = new ProductoListAdapter(new ProductoListAdapter.ProductoDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mProductoViewModel = new ViewModelProvider(getActivity()).get(ProductoViewModel.class);
        mProductoViewModel.getAllProductos().observe(getActivity(), productos -> {
            adapter.submitList(productos);
        });

        try {
            String codigo = getArguments().getString("CODIGO");
            String descripcion = getArguments().getString("DESCRIPCION");
            Integer cantidad = getArguments().getInt("CANTIDAD");
            Double costo = getArguments().getDouble("COSTO");
            Producto producto = new Producto(codigo, descripcion,cantidad,costo);
            mProductoViewModel.insert(producto);
        } catch(Exception e) {
            e.printStackTrace();
        }

        optManual = view.findViewById(R.id.optManual);
        optManual.setOnClickListener( v -> {
                Navigation.findNavController(view).navigate(R.id.cotizationToRegistro);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Producto producto = new Producto(data.getStringExtra("CODIGO"),
                    data.getStringExtra("DESCRIPCION"),
                    data.getIntExtra("CANTIDAD", 1),
                    data.getDoubleExtra("COSTO", 0));
            mProductoViewModel.insert(producto);
        } else {
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
}