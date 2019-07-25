package com.humanid.auth.data.source.remote.api;

import android.support.annotation.NonNull;

import com.humanid.auth.data.source.remote.api.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.otp.verify.VerifyOTPRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterResponse;
import com.humanid.auth.data.source.remote.api.otp.verify.VerifyOTPResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPIService {

    @POST("users/verifyPhone")
    Single<VerifyOTPResponse> verifyOTP(@NonNull @Body VerifyOTPRequest request);

    @POST("users/register")
    Single<RegisterResponse> register(@NonNull @Body RegisterRequest request);

    @POST("users/login")
    Single<LoginResponse> login(@NonNull @Body LoginRequest request);
}
