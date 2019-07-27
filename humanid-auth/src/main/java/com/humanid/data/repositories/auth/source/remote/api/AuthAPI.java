package com.humanid.data.repositories.auth.source.remote.api;

import android.support.annotation.NonNull;

import com.humanid.HttpClient;
import com.humanid.data.repositories.auth.source.remote.api.login.LoginRequest;
import com.humanid.data.repositories.auth.source.remote.api.login.LoginResponse;
import com.humanid.data.repositories.auth.source.remote.api.login.check.CheckLoginResponse;
import com.humanid.data.repositories.auth.source.remote.api.otp.OTPRequest;
import com.humanid.data.repositories.auth.source.remote.api.otp.OTPResponse;
import com.humanid.data.repositories.auth.source.remote.api.register.RegisterRequest;
import com.humanid.data.repositories.auth.source.remote.api.register.RegisterResponse;
import com.humanid.internal.Validate;

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

    @NonNull
    @Override
    public Single<OTPResponse> requestOTP(@NonNull OTPRequest request) {
        Validate.checkNotNull(request, "OTPRequest cannot be null.");
        Validate.checkState(service != null, "AuthAPIService has not been initialized.");

        return service.requestOTP(request);
    }

    @NonNull
    @Override
    public Single<RegisterResponse> register(@NonNull RegisterRequest request) {
        Validate.checkNotNull(request, "RegisterRequest cannot be null.");
        Validate.checkState(service != null, "AuthAPIService has not been initialized.");

        return service.register(request);
    }

    @NonNull
    @Override
    public Single<LoginResponse> login(@NonNull LoginRequest request) {
        Validate.checkNotNull(request, "LoginRequest cannot be null.");
        Validate.checkState(service != null, "AuthAPIService has not been initialized.");

        return service.login(request);
    }

    @NonNull
    @Override
    public Single<CheckLoginResponse> checkLogin() {
        Validate.checkState(service != null, "AuthAPIService has not been initialized.");

        return service.checkLogin();
    }
}
