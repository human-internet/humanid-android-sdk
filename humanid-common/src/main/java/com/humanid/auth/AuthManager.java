package com.humanid.auth;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.HumanIDSDK;
import com.humanid.internal.Validate;

public class AuthManager {

    private final static String TAG = AuthManager.class.getSimpleName();

    private static volatile AuthManager INSTANCE;

    private Context applicationContext;

    private AuthManager() {
        Validate.sdkInitialized();
        this.applicationContext = HumanIDSDK.getInstance().getApplicationContext();
    }

    @NonNull
    public static AuthManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthManager();
                }
            }
        }

        return INSTANCE;
    }
}
