package com.humanid.internal.data.source.local.preference.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.internal.data.source.local.preference.BasePreference;

public class UserPreference extends BasePreference {

    private final static String TAG = UserPreference.class.getSimpleName();

    public enum Key {
        USER_HASH
    }

    private static volatile UserPreference INSTANCE;

    private Context applicationContext;

    private UserPreference(@NonNull Context applicationContext) {
        super(applicationContext);
        this.applicationContext = applicationContext;
    }

    @NonNull
    public static UserPreference getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            synchronized (UserPreference.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserPreference(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    public String getUserHash() {
        return getString(Key.USER_HASH.name());
    }

    public void setUserHash(@NonNull String userHas) {
        put(Key.USER_HASH.name(), userHas);
    }
}
