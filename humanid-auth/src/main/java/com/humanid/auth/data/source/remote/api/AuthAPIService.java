package com.humanid.auth.data.source.remote.api;

import android.support.annotation.NonNull;

import com.humanid.auth.data.source.remote.api.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterResponse;
import com.humanid.auth.data.source.remote.api.otp.OTPResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPIService {

    @NonNull
    @POST("users/verifyPhone")
    Single<OTPResponse> requestOTP(@NonNull @Body OTPRequest request);

    @NonNull
    @POST("users/register")
    Single<RegisterResponse> register(@NonNull @Body RegisterRequest request);

    @NonNull
    @POST("users/login")
    Single<LoginResponse> login(@NonNull @Body LoginRequest request);

    @NonNull
    @POST("users/login")
    Single<CheckLoginResponse> checkLogin();
}
