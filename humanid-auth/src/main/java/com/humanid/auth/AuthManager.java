package com.humanid.auth;

import android.support.annotation.NonNull;

public class AuthManager {

    private final static String TAG = AuthManager.class.getSimpleName();

    private static volatile AuthManager INSTANCE;

    private AuthManager() {

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
