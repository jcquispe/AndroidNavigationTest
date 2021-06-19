package com.muvlin.app.apiclient;

import com.muvlin.app.apiclient.pojo.BasicResponse;
import com.muvlin.app.apiclient.pojo.LoginRequest;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthInterface {
    @Headers({ "Content-Type: application/json"})
    @POST("auth/login")
    Observable<BasicResponse> login(@Body LoginRequest request);

    @Headers({"Content-Type: application/json", "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjYXJsYW1AbXV2bGluLmNvbSIsImlhdCI6MTYyMzk3Njk0MSwiZXhwIjoxNjI0MDYzMzQxLCJvcmlnZW4iOiJNT0JJTEUifQ.JG4easG-tDB4JcWA-oghiUUyS3y_zoKew-oK0gLNPat1nkEnARUjSDDLbTlgXS-qbaHVACsc9AaqYBsoMxtL6g"})
    @GET("auth/token")
    Observable<BasicResponse> verifyToken();
}
