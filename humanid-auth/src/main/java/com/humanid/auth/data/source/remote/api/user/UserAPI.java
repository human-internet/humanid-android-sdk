package com.humanid.auth.data.source.remote.api.user;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.data.source.remote.api.HttpClient;
import com.humanid.auth.data.source.remote.api.user.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.user.register.RegisterResponse;
import com.humanid.auth.data.source.remote.api.user.update.UpdateRequest;
import com.humanid.auth.data.source.remote.api.user.update.UpdateResponse;
import com.humanid.auth.data.source.remote.api.user.updatephone.UpdatePhoneRequest;
import com.humanid.auth.data.source.remote.api.user.updatephone.UpdatePhoneResponse;
import com.humanid.util.Preconditions;

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
        Preconditions.checkNotNull(request, "OTPRequest cannot be null.");

        return service.requestOTP(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<RegisterResponse>> register(@NonNull RegisterRequest request) {
        Preconditions.checkNotNull(request, "RegisterRequest cannot be null.");

        return service.register(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<LoginResponse>> login(@NonNull LoginRequest request) {
        Preconditions.checkNotNull(request, "LoginRequest cannot be null.");

        return service.login(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<CheckLoginResponse>> checkLogin(
            @NonNull String userHash, @NonNull String applicationID,
            @NonNull String applicationSecret) {
        Preconditions.checkArgument(!TextUtils.isEmpty(userHash), "userHash");
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");

        return service.checkLogin(userHash, applicationID, applicationSecret);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<UpdateResponse>> update(@NonNull UpdateRequest request) {
        Preconditions.checkNotNull(request, "UpdateRequest cannot be null.");

        return service.update(request);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<UpdatePhoneResponse>> updatePhone(
            @NonNull UpdatePhoneRequest request) {
        Preconditions.checkNotNull(request, "UpdatePhoneRequest cannot be null.");

        return service.updatePhone(request);
    }
}
