package com.humanid.auth.data.source.remote.api.user;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.APIResponse;
import com.humanid.HttpClient;
import com.humanid.auth.data.source.remote.api.user.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.user.register.RegisterResponse;
import com.humanid.internal.Validate;

public class UserAPI implements UserAPIService {

    private final static String TAG = UserAPI.class.getSimpleName();

    private static volatile UserAPI INSTANCE;

    private final Context applicationContext;
    private final UserAPIService service;

    private UserAPI(@NonNull Context applicationContext,
                    @NonNull UserAPIService userAPIService) {
        this.applicationContext = applicationContext;
        this.service = userAPIService;
    }

    @NonNull
    public static UserAPI getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (UserAPI.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();
                    UserAPIService userAPIService = HttpClient.createService(UserAPIService.class);

                    INSTANCE = new UserAPI(applicationContext, userAPIService);
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    public LiveData<APIResponse<OTPResponse>> requestOTP(@NonNull OTPRequest request) {
        Validate.checkNotNull(request, "OTPRequest cannot be null.");

        return service.requestOTP(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<RegisterResponse>> register(@NonNull RegisterRequest request) {
        Validate.checkNotNull(request, "RegisterRequest cannot be null.");

        return service.register(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<LoginResponse>> login(@NonNull LoginRequest request) {
        Validate.checkNotNull(request, "LoginRequest cannot be null.");

        return service.login(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<CheckLoginResponse>> checkLogin() {

        return service.checkLogin();
    }
}
