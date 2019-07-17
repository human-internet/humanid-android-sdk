package com.humanid.internal.data.source.local.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class AppSharedPreference {

    private final static String TAG = AppSharedPreference.class.getSimpleName();

    private static final String PREFERENCE_AUTH = "com.humanid";

    private static volatile AppSharedPreference INSTANCE;

    private Context applicationContext;
    private final SharedPreferences sharedPreferences;

    public enum Key {
        APP_ID, APP_SECRET,
        USER_HASH
    }

    private AppSharedPreference(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = applicationContext
                .getSharedPreferences(PREFERENCE_AUTH, Context.MODE_PRIVATE);
    }

    @NonNull
    public static AppSharedPreference getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            synchronized (AppSharedPreference.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppSharedPreference(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    public void put(Key key, String val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key.name(), val);
        editor.apply();
    }

    public String getString(Key key) {
        return sharedPreferences.getString(key.name(), null);
    }
}
