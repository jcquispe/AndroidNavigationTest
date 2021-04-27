package com.muvlin.app.apiclient;

import com.muvlin.app.apiclient.pojo.LoginRequest;
import com.muvlin.app.apiclient.pojo.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({ "Content-Type: application/json"})
    @POST("oauth/token")
    Observable<LoginResponse> login(@Body LoginRequest login);
}
