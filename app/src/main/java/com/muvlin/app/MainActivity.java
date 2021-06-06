package com.muvlin.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.muvlin.app.R;
import com.muvlin.app.database.AppDatabase;
import com.muvlin.app.database.dao.ProductoDao;

import java.util.concurrent.Executors;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MySettings" ;
    public static final String Margen = "margenKey";
    SharedPreferences sharedpreferences;
    private ProductoViewModel mProductoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferencesManager.getInstance().init(this);

        mProductoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (getMargen() == 0) {
            saveDefaultMargen();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cotizacion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.generate:
                moveToGenerate();
                return true;
            case R.id.settings:
                moveToSettings();
                return true;
            case R.id.clean:
                deleteAll();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return false;
        }
    }

    private void moveToGenerate() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, ReportFragment.class, null);
        transaction.addToBackStack("Volver");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private void moveToSettings() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, AjustesFragment.class, null);
        transaction.addToBackStack("Volver");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private void saveDefaultMargen() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(Margen, 25);
        editor.commit();
    }

    private Float getMargen() {
        return sharedpreferences.getFloat(Margen, 0);
    }

    public void deleteAll() {
        mProductoViewModel.deleteAll();
    }

    private void logout() {
        finish();
        System.exit(0);
    }
}