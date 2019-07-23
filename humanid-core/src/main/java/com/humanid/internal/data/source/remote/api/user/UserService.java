package com.humanid.internal.data.source.remote.api.user;

import com.humanid.internal.data.source.remote.api.user.login.LoginResponse;
import com.humanid.internal.data.source.remote.api.user.register.RegisterResponse;
import com.humanid.internal.data.source.remote.api.user.otp.verify.VerifyOTPResponse;

import io.reactivex.Single;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/verifyPhone")
    Single<VerifyOTPResponse> verifyOTP();

    @POST("users/register")
    Single<RegisterResponse> register();

    @POST("users/login")
    Single<LoginResponse> login();
}
