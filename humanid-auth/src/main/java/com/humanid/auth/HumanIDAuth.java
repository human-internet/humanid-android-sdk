package com.humanid.auth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.humanid.HumanIDSDK;
import com.humanid.auth.data.model.User;
import com.humanid.auth.data.repositories.UserRepository;
import com.humanid.auth.tasks.CheckLoginTask;
import com.humanid.auth.tasks.LoginTask;
import com.humanid.auth.tasks.LogoutTask;
import com.humanid.auth.tasks.RegisterTask;
import com.humanid.auth.tasks.RequestOTPTask;
import com.humanid.task.Task;

import java.util.ArrayList;
import java.util.List;

public class HumanIDAuth {

    private final static String TAG = HumanIDAuth.class.getSimpleName();

    private static volatile HumanIDAuth INSTANCE;

    private final Context applicationContext;
    private HumanIDUser humanIDUser;

    private List<AuthChangeListener> authChangeListeners = new ArrayList<>();

    private HumanIDAuth(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    public static HumanIDAuth getInstance() {
        if (INSTANCE == null) {
            synchronized (HumanIDAuth.class) {
                if (INSTANCE == null) {
                    Context applicationContext = HumanIDSDK.getInstance().getApplicationContext();

                    INSTANCE = new HumanIDAuth(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    public void addAuthChangeListener(@NonNull AuthChangeListener listener) {
        authChangeListeners.add(listener);
    }

    public void removeAuthChangeListener(@NonNull AuthChangeListener listener) {
        authChangeListeners.remove(listener);
    }

    @Nullable
    public HumanIDUser getCurrentUser() {
        loadCurrentUser();
        return humanIDUser;
    }

    private void loadCurrentUser() {
        User user = UserRepository.getInstance(applicationContext).getCurrentUser();
        if (user == null) return;
        String userHash = user.getUserHash();
        if (TextUtils.isEmpty(userHash)) return;
        humanIDUser = new HumanIDUser(userHash);
    }

    @NonNull
    public Task<String> requestOTP(@NonNull String countryCode, @NonNull String phone) {
        return new RequestOTPTask(countryCode, phone);
    }

    @NonNull
    public Task<HumanIDUser> register(@NonNull String countryCode, @NonNull String phone,
                                     @NonNull String verificationCode) {
         return new RegisterTask(countryCode, phone, verificationCode);
    }

    @NonNull
    public Task<HumanIDUser> login(@NonNull String existingUserHash) {
        return new LoginTask(existingUserHash);
    }

    @NonNull
    public Task<String> checkLogin() {
        return new CheckLoginTask();
    }

    @NonNull
    public Task<Void> logout() {
        return new LogoutTask();
    }
}
