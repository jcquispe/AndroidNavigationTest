package com.muvlin.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muvlin.app.MainActivity;
import com.muvlin.app.R;
import com.muvlin.app.apiclient.ApiClient;
import com.muvlin.app.apiclient.ApiInterface;
import com.muvlin.app.apiclient.pojo.LoginRequest;
import com.muvlin.app.apiclient.pojo.LoginResponse;
import com.muvlin.app.util.Alert;
import com.muvlin.app.util.CustomProgressBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CustomProgressBar progressBar = new CustomProgressBar();

    EditText textEmail, textPass;
    Button buttonEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textEmail = findViewById(R.id.editTextEmail);
        textPass = findViewById(R.id.editTextPass);
        buttonEntrar = findViewById(R.id.buttonEntrar);

        buttonEntrar.setOnClickListener( v -> {
            progressBar.show(LoginActivity.this, "Autenticando...");
            LoginRequest request = new LoginRequest();
            request.setUsername(textEmail.getText().toString().trim());
            request.setPassword(textPass.getText().toString().trim());
            request.setGrant_type("password");
            request.setClient_id(getString(R.string.client_id));
            request.setClient_secret(getString(R.string.client_secret));

            Retrofit retrofit = ApiClient.getInstance();
            apiInterface = retrofit.create(ApiInterface.class);
            compositeDisposable.add(apiInterface.login(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LoginResponse>() {
                        @Override
                        public void accept(LoginResponse loginResponse) throws Exception {
                            if (loginResponse.access_token == null) {
                                Alert alert = new Alert();
                                alert.simpleAlert(LoginActivity.this, "ATENCIÓN", "Usuario no autenticado, verifique e intentelo nuevamente", "OK");
                                //loadingLyt.setVisibility(View.GONE);
                                //loginLyt.setVisibility(View.VISIBLE);
                                progressBar.getDialog().dismiss();
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                progressBar.getDialog().dismiss();
                                startActivity(intent);
                            }


                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }));
        });
    }
}