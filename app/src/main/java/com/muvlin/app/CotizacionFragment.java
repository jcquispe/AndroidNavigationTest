package com.muvlin.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.muvlin.app.R;
import com.muvlin.app.database.model.Producto;
import com.nambimobile.widgets.efab.FabOption;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CotizacionFragment extends Fragment {

    private FabOption optManual, optListado, optGenerar;
    private ProductoViewModel mProductoViewModel;
    public static final String MyPREFERENCES = "MySettings" ;
    public static final String Margen = "margenKey";
    SharedPreferences sharedpreferences;
    Double total = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_cotizacion, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerviewCotizacion);
        final ProductoListAdapter adapter = new ProductoListAdapter(new ProductoListAdapter.ProductoDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mProductoViewModel = new ViewModelProvider(getActivity()).get(ProductoViewModel.class);
        mProductoViewModel.getAllProductos().observe(getActivity(), productos -> {
            total = 0.0;
            for(Producto p: productos) {
                Calculos calculos = new Calculos();
                Double precio = calculos.calculaPrecio(p.getCosto(), getMargen());
                total += p.getCantidad() * precio;
            }
            adapter.submitList(productos);
        });

        try {
            String codigo = getArguments().getString("CODIGO");
            String descripcion = getArguments().getString("DESCRIPCION");
            Integer cantidad = getArguments().getInt("CANTIDAD");
            Double costo = getArguments().getDouble("COSTO");
            Calculos calcular = new Calculos();
            Double precio = calcular.calculaPrecio(costo, getMargen());
            Producto producto = new Producto(codigo,descripcion,cantidad,costo,precio);
            mProductoViewModel.insert(producto);
        } catch(Exception e) {
            e.printStackTrace();
        }

        optManual = view.findViewById(R.id.optManual);
        optGenerar = view.findViewById(R.id.optGenerar);
        optManual.setOnClickListener( v -> {
            Navigation.findNavController(view).navigate(R.id.cotizationToRegistro);
        });
        optGenerar.setOnClickListener( v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("TOTAL");
            Double totalRedondeado = Math.round((total) * 100.0) / 100.0;
            Double comision = Math.round((totalRedondeado * 0.05) * 100.0) / 100.0;
            builder.setMessage("Costo para el cliente " + totalRedondeado +" Bs.\nMargen de cotización " + SharedPreferencesManager.getInstance().getMargen() + "%\nComisión " + comision + " Bs.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return view;
    }

    private Float getMargen() {
        return sharedpreferences.getFloat(Margen, 0);
    }
}