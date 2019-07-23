package com.humanid.internal.data.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.internal.Validate;
import com.humanid.internal.data.source.local.preference.user.UserPreference;

public class UserRepository {

    private final static String TAG = UserRepository.class.getSimpleName();

    private static volatile UserRepository INSTANCE;

    private Context applicationContext;
    private UserPreference preference;

    private UserRepository(@NonNull Context applicationContext) {
        Validate.sdkInitialized();
        this.applicationContext = applicationContext;
        preference = UserPreference.getInstance(applicationContext);
    }

    @NonNull
    public static UserRepository getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    public String getUserHash() {
        return preference.getUserHash();
    }

    public void setUserHash(@NonNull String userHash) {
        preference.setUserHash(userHash);
    }
}
