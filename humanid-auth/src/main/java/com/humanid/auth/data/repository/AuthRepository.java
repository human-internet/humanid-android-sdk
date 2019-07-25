package com.humanid.auth.data.repository;

import android.support.annotation.NonNull;

import com.humanid.AccessToken;
import com.humanid.AccessTokenManager;
import com.humanid.HumanIDSDK;
import com.humanid.auth.data.source.remote.api.AuthAPI;
import com.humanid.auth.data.source.remote.api.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.register.RegisterResponse;
import com.humanid.auth.internal.DeviceIdentifierManager;

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

    public Single<AccessToken> register(@NonNull String countryCode, @NonNull String phone,
                                        @NonNull String verificationCode) {
        RegisterRequest request = new RegisterRequest(countryCode, phone, verificationCode,
                DeviceIdentifierManager.getInstance().getDeviceID(),
                DeviceIdentifierManager.getInstance().getNotificationID());

        return authAPI.register(request)
                .map(new Function<RegisterResponse, AccessToken>() {
                    @Override
                    public AccessToken apply(RegisterResponse response) throws Exception {
                        return generateAccessToken(response.getHash());
                    }
                });
    }

    public Single<AccessToken> login(@NonNull String existingHash) {
        LoginRequest request = new LoginRequest(existingHash,
                DeviceIdentifierManager.getInstance().getNotificationID());

        return authAPI.login(request)
                .map(new Function<LoginResponse, AccessToken>() {
                    @Override
                    public AccessToken apply(LoginResponse response) throws Exception {
                        return generateAccessToken(response.getHash());
                    }
                });
    }

    private AccessToken generateAccessToken(@NonNull String userHash) {
        AccessToken accessToken = new AccessToken(
                userHash,
                DeviceIdentifierManager.getInstance().getDeviceID(),
                DeviceIdentifierManager.getInstance().getNotificationID(),
                HumanIDSDK.getInstance().getOptions().getApplicationID(),
                HumanIDSDK.getInstance().getOptions().getApplicationSecret()
        );

        AccessTokenManager.getInstance().setCurrentAccessToken(accessToken);
        return AccessTokenManager.getInstance().getCurrentAccessToken();
    }
}
