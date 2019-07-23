package com.humanid.internal.data.source.remote.api.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.internal.data.source.remote.api.BaseAPI;
import com.humanid.internal.data.source.remote.api.user.login.LoginRequest;
import com.humanid.internal.data.source.remote.api.user.login.LoginResponse;
import com.humanid.internal.data.source.remote.api.user.otp.verify.VerifyOTPRequest;
import com.humanid.internal.data.source.remote.api.user.otp.verify.VerifyOTPResponse;
import com.humanid.internal.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.internal.data.source.remote.api.user.register.RegisterResponse;

import io.reactivex.Single;

public class UserAPI extends BaseAPI implements UserService {

    private final static String TAG = UserAPI.class.getSimpleName();

    private static volatile UserAPI INSTANCE;

    private UserService service;

    private UserAPI(@NonNull Context applicationContext) {
        super(applicationContext);
        this.service = createService(UserService.class);
    }

    @NonNull
    public static UserAPI getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            synchronized (UserAPI.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserAPI(applicationContext);
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
