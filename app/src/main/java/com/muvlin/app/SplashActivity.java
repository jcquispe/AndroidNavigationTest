package com.muvlin.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.muvlin.app.apiclient.AuthClient;
import com.muvlin.app.apiclient.AuthInterface;
import com.muvlin.app.apiclient.pojo.BasicResponse;
import com.muvlin.app.apiclient.pojo.LoginRequest;
import com.muvlin.app.login.LoginActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {

    AuthInterface authInterface;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView textVersion;

    public static final String MyPREFERENCES = "MySettings" ;
    public static final String Token = "tokenKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        textVersion = findViewById(R.id.textVersion);
        textVersion.setText("version " + BuildConfig.VERSION_NAME);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if (!getToken().equals("")) {
                    Retrofit retrofit = AuthClient.getInstance();
                    authInterface = retrofit.create(AuthInterface.class);
                    compositeDisposable.add(authInterface.verifyToken()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BasicResponse>() {
                                @Override
                                public void accept(BasicResponse basicResponse) throws Exception {
                                    if (basicResponse.getSuccess()) {
                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                    );
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        }, 2000);


    }

    private String getToken() {
        return sharedpreferences.getString(Token, "");
    }
}