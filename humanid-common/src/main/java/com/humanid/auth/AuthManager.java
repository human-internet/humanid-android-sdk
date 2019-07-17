package com.humanid.auth;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.HumanIDSDK;
import com.humanid.internal.Validate;
import com.humanid.internal.data.DataRepository;

public class AuthManager {

    private final static String TAG = AuthManager.class.getSimpleName();

    private static volatile AuthManager INSTANCE;

    private Context applicationContext;
    private final DataRepository dataRepository;

    private AuthManager() {
        Validate.sdkInitialized();
        this.applicationContext = HumanIDSDK.getInstance().getApplicationContext();
        this.dataRepository = DataRepository.getInstance(applicationContext);
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
