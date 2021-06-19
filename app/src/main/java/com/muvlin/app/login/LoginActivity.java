package com.muvlin.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.muvlin.app.MainActivity;
import com.muvlin.app.R;
import com.muvlin.app.apiclient.AuthClient;
import com.muvlin.app.apiclient.AuthInterface;
import com.muvlin.app.apiclient.pojo.BasicResponse;
import com.muvlin.app.apiclient.pojo.LoginRequest;
import com.muvlin.app.util.Alert;
import com.muvlin.app.util.CustomProgressBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    AuthInterface authInterface;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CustomProgressBar progressBar = new CustomProgressBar();

    EditText textEmail, textPass;
    Button buttonEntrar;

    public static final String MyPREFERENCES = "MySettings" ;
    public static final String Token = "tokenKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_login);
        textEmail = findViewById(R.id.editTextEmail);
        textPass = findViewById(R.id.editTextPass);
        buttonEntrar = findViewById(R.id.buttonEntrar);

        buttonEntrar.setOnClickListener( v -> {
            progressBar.show(LoginActivity.this, "Autenticando...");
            String email = textEmail.getText().toString().trim();
            String pass = textPass.getText().toString();
            LoginRequest request = new LoginRequest(email, pass, "MOBILE");

            Retrofit retrofit = AuthClient.getInstance();
            authInterface = retrofit.create(AuthInterface.class);
            compositeDisposable.add(authInterface.login(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BasicResponse>() {
                        @Override
                        public void accept(BasicResponse loginResponse) throws Exception {
                            if (loginResponse.getSuccess()) {
                                Object obj = loginResponse.getMessage();
                                LinkedTreeMap<Object, Object> user = (LinkedTreeMap) obj;

                                savePreferencesData(Token, user.get("pass").toString());
                                savePreferencesData("emailKey", user.get("email").toString());
                                savePreferencesData("firstNameKey", user.get("firstName").toString());
                                savePreferencesData("lastNameKey", user.get("lastName").toString());
                                savePreferencesData("imageKey", user.get("image").toString());

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                progressBar.getDialog().dismiss();
                                startActivity(intent);
                            }
                            else {
                                Alert alert = new Alert();
                                alert.simpleAlert(LoginActivity.this, "ATENCIÓN", loginResponse.getMessage().toString(), "OK");
                                progressBar.getDialog().dismiss();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progressBar.getDialog().dismiss();
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                        }
                    }));
        });
    }

    private void savePreferencesData(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}