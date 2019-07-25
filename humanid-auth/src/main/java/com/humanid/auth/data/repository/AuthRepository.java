package com.humanid.auth.data.repository;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.humanid.AccessToken;
import com.humanid.HumanIDSDK;
import com.humanid.auth.data.source.remote.api.AuthAPI;
import com.humanid.auth.data.source.remote.api.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterResponse;
import com.humanid.auth.internal.DeviceIdentifierManager;
import com.humanid.internal.Validate;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class AuthRepository {

    private final static String TAG = AuthRepository.class.getSimpleName();

    private static volatile AuthRepository INSTANCE;

    private AuthAPI authAPI;

    private AuthRepository() {
        this.authAPI = AuthAPI.getInstance();
    }

    @NonNull
    public static AuthRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthRepository();
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    public Single<Boolean> requestOTP(@NonNull String countryCode, @NonNull String phone) {
        Validate.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
        Validate.checkArgument(!TextUtils.isEmpty(phone), "phone");
        Validate.checkState(authAPI != null, "AuthAPI cannot be null.");

        OTPRequest request = new OTPRequest(countryCode, phone);

        return authAPI.requestOTP(request)
                .map(new Function<OTPResponse, Boolean>() {
                    @Override
                    public Boolean apply(OTPResponse response) throws Exception {
                        return true;
                    }
                });
    }

    @NonNull
    public Single<AccessToken> register(@NonNull String countryCode, @NonNull String phone,
                                     @NonNull String verificationCode) {
        Validate.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
        Validate.checkArgument(!TextUtils.isEmpty(phone), "phone");
        Validate.checkArgument(!TextUtils.isEmpty(verificationCode), "verificationCode");
        Validate.checkState(authAPI != null, "AuthAPI cannot be null.");

        RegisterRequest request = new RegisterRequest(countryCode, phone, verificationCode,
                DeviceIdentifierManager.getInstance().getDeviceID(),
                DeviceIdentifierManager.getInstance().getNotificationID());

        return authAPI.register(request)
                .map(new Function<RegisterResponse, AccessToken>() {
                    @Override
                    public AccessToken apply(RegisterResponse response) throws Exception {
                        return generateAccessToken(response.getUserHash());
                    }
                });
    }

    @NonNull
    public Single<AccessToken> login(@NonNull String existingUserHash) {
        Validate.checkArgument(!TextUtils.isEmpty(existingUserHash), "existingUserHash");
        Validate.checkState(authAPI != null, "AuthAPI cannot be null.");

        LoginRequest request = new LoginRequest(existingUserHash,
                DeviceIdentifierManager.getInstance().getNotificationID());

        return authAPI.login(request)
                .map(new Function<LoginResponse, AccessToken>() {
                    @Override
                    public AccessToken apply(LoginResponse response) throws Exception {
                        return generateAccessToken(response.getUserHash());
                    }
                });
    }

    @NonNull
    public Single<Boolean> checkLogin() {
        Validate.checkState(authAPI != null, "AuthAPI cannot be null.");

        return authAPI.checkLogin()
                .map(new Function<CheckLoginResponse, Boolean>() {
                    @Override
                    public Boolean apply(CheckLoginResponse response) throws Exception {
                        return true;
                    }
                });
    }

    @NonNull
    private AccessToken generateAccessToken(@NonNull String userHash) {
        Validate.checkArgument(!TextUtils.isEmpty(userHash), "userHash");

        return new AccessToken(
                userHash,
                DeviceIdentifierManager.getInstance().getDeviceID(),
                DeviceIdentifierManager.getInstance().getNotificationID(),
                HumanIDSDK.getInstance().getOptions().getApplicationID(),
                HumanIDSDK.getInstance().getOptions().getApplicationSecret()
        );
    }
}
