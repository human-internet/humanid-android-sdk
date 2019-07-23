package com.humanid.internal.data.source.local.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class BasePreference {

    private final static String TAG = BasePreference.class.getSimpleName();

    private static final String PREFERENCE_AUTH = "com.humanid";

    private Context applicationContext;
    private final SharedPreferences sharedPreferences;

    protected BasePreference(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = applicationContext
                .getSharedPreferences(PREFERENCE_AUTH, Context.MODE_PRIVATE);
    }

    protected void put(@NonNull String key, String val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    protected String getString(@NonNull String key) {
        return sharedPreferences.getString(key, null);
    }

    protected String getString(@NonNull String key, @Nullable String defValue) {
        return sharedPreferences.getString(key, defValue);
    }
}
