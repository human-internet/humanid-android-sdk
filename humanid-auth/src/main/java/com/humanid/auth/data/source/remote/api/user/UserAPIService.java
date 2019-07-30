package com.humanid.auth.data.source.remote.api.user;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.humanid.APIResponse;
import com.humanid.auth.data.source.remote.api.user.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.user.register.RegisterResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPIService {

    @NonNull
    @POST("users/verifyPhone")
    LiveData<APIResponse<OTPResponse>> requestOTP(@NonNull @Body OTPRequest request);

    @NonNull
    @POST("users/register")
    LiveData<APIResponse<RegisterResponse>> register(@NonNull @Body RegisterRequest request);

    @NonNull
    @POST("users/login")
    LiveData<APIResponse<LoginResponse>> login(@NonNull @Body LoginRequest request);

    @NonNull
    @POST("users/login")
    LiveData<APIResponse<CheckLoginResponse>> checkLogin();
}
