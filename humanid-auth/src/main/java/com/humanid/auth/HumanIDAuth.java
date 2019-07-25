package com.humanid.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.humanid.AccessToken;
import com.humanid.AccessTokenManager;
import com.humanid.auth.tasks.AuthResult;
import com.humanid.auth.tasks.CheckLoginResult;
import com.humanid.auth.tasks.CheckLoginTask;
import com.humanid.auth.tasks.LoginTask;
import com.humanid.auth.tasks.LogoutTask;
import com.humanid.auth.tasks.RegisterTask;
import com.humanid.auth.tasks.RequestOTPResult;
import com.humanid.auth.tasks.RequestOTPTask;
import com.humanid.task.Task;

public class HumanIDAuth {

    private final static String TAG = HumanIDAuth.class.getSimpleName();

    private static volatile HumanIDAuth INSTANCE;

    private HumanIDUser humanIDUser;

    private HumanIDAuth() {

    }

    @NonNull
    public static HumanIDAuth getInstance() {
        if (INSTANCE == null) {
            synchronized (HumanIDAuth.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HumanIDAuth();
                }
            }
        }

        return INSTANCE;
    }

    @Nullable
    public HumanIDUser getCurrentUser() {
        loadCurrentUser();
        return humanIDUser;
    }

    private void loadCurrentUser() {
        AccessToken accessToken = AccessTokenManager.getInstance().getCurrentAccessToken();
        if (accessToken == null) return;
        String userHash = accessToken.getUserHash();
        if (TextUtils.isEmpty(userHash)) return;
        humanIDUser = new HumanIDUser(userHash);
    }

    @NonNull
    public Task<RequestOTPResult> requestOTP(@NonNull String countryCode, @NonNull String phone) {
        return new RequestOTPTask(countryCode, phone);
    }

    @NonNull
    public Task<AuthResult> register(@NonNull String countryCode, @NonNull String phone,
                                     @NonNull String verificationCode) {
         return new RegisterTask(countryCode, phone, verificationCode);
    }

    @NonNull
    public Task<AuthResult> login(@NonNull String existingUserHash) {
        return new LoginTask(existingUserHash);
    }

    @NonNull
    public Task<CheckLoginResult> checkLogin() {
        return new CheckLoginTask();
    }

    @NonNull
    public Task<Void> logout() {
        return new LogoutTask();
    }
}
