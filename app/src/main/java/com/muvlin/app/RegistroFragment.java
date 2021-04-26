package com.muvlin.app;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.muvlin.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {

    private EditText editTextCodigo, editTextDescripcion, editTextCantidad, editTextCosto;
    private Button buttonRegistrar;
    Util util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        editTextCodigo = view.findViewById(R.id.editTextCodigo);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextCantidad = view.findViewById(R.id.editTextCantidad);
        editTextCosto = view.findViewById(R.id.editTextCosto);
        buttonRegistrar = view.findViewById(R.id.buttonRegistrar);

        buttonRegistrar.setOnClickListener(v -> {
            String cod = editTextCodigo.getText().toString().trim();
            String des = editTextDescripcion.getText().toString().trim();
            String can = editTextCantidad.getText().toString().trim();
            String cos = editTextCosto.getText().toString().trim();

            if (cod.equals("") || des.equals("") || can.equals("") || cos.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("ATENCIÓN");
                builder.setMessage("Todos los campos son requeridos");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Bundle i = new Bundle();
                i.putString("CODIGO", cod);
                i.putString("DESCRIPCION", des);
                i.putInt("CANTIDAD", Integer.parseInt(can));
                i.putDouble("COSTO", Double.parseDouble(cos));

                Navigation.findNavController(view).navigate(R.id.registroToCotizacion, i);
            }

            /*Intent replyIntent = new Intent();
            replyIntent.putExtra("CODIGO", editTextCodigo.getText().toString().trim());
            replyIntent.putExtra("DESCRIPCION", editTextDescripcion.getText().toString().trim());
            replyIntent.putExtra("CANTIDAD", Integer.parseInt(editTextCantidad.getText().toString().trim()));
            replyIntent.putExtra("COSTO", Double.parseDouble(editTextCosto.getText().toString().trim()));
            getActivity().setResult(Activity.RESULT_OK, replyIntent);*/
        });

        return view;
    }
}