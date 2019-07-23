package com.humanid.internal.data.source.remote.api.user;

import android.support.annotation.NonNull;

import com.humanid.internal.data.source.remote.api.user.login.LoginRequest;
import com.humanid.internal.data.source.remote.api.user.login.LoginResponse;
import com.humanid.internal.data.source.remote.api.user.otp.verify.VerifyOTPRequest;
import com.humanid.internal.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.internal.data.source.remote.api.user.register.RegisterResponse;
import com.humanid.internal.data.source.remote.api.user.otp.verify.VerifyOTPResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/verifyPhone")
    Single<VerifyOTPResponse> verifyOTP(@NonNull @Body VerifyOTPRequest request);

    @POST("users/register")
    Single<RegisterResponse> register(@NonNull @Body RegisterRequest request);

    @POST("users/login")
    Single<LoginResponse> login(@NonNull @Body LoginRequest request);
}
