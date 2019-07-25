package com.humanid.auth.data.source.remote.api;

import android.support.annotation.NonNull;

import com.humanid.HttpClient;
import com.humanid.auth.data.source.remote.api.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.otp.verify.VerifyOTPRequest;
import com.humanid.auth.data.source.remote.api.otp.verify.VerifyOTPResponse;
import com.humanid.auth.data.source.remote.api.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterResponse;

import io.reactivex.Single;

public class AuthAPI implements AuthAPIService {

    private final static String TAG = AuthAPI.class.getSimpleName();

    private static volatile AuthAPI INSTANCE;

    private AuthAPIService service;

    private AuthAPI() {
        this.service = HttpClient.createService(AuthAPIService.class);
    }

    @NonNull
    public static AuthAPI getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthAPI.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthAPI();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public Single<VerifyOTPResponse> verifyOTP(@NonNull VerifyOTPRequest request) {
        return service.verifyOTP(request);
    }

    @Override
    public Single<RegisterResponse> register(@NonNull RegisterRequest request) {
        return service.register(request);
    }

    @Override
    public Single<LoginResponse> login(@NonNull LoginRequest request) {
        return service.login(request);
    }
}
