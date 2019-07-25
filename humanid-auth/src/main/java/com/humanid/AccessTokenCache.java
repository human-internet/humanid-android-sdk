package com.humanid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.humanid.internal.Validate;

class AccessTokenCache {

    private final static String TAG = AccessToken.class.getSimpleName();

    private final static String CACHED_ACCESS_TOKEN_KEY = "CACHED_ACCESS_TOKEN";

    private final SharedPreferences sharedPreferences;

    AccessTokenCache() {
        this.sharedPreferences = HumanIDSDK.getInstance().getApplicationContext()
                .getSharedPreferences(AccessTokenManager.SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE);
    }

    @Nullable
    AccessToken load() {
        AccessToken accessToken = null;

        if (hasCachedAccessToken()) {
            accessToken = getCachedAccessToken();
        }

        return accessToken;
    }

    void save(@NonNull AccessToken accessToken) {
        Validate.checkNotNull(accessToken, "AccessToken cannot be null.");
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        sharedPreferences.edit()
                .putString(CACHED_ACCESS_TOKEN_KEY, new Gson().toJson(accessToken))
                .apply();
    }

    void clear() {
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
    }

    private boolean hasCachedAccessToken() {
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        return sharedPreferences.contains(CACHED_ACCESS_TOKEN_KEY);
    }

    @Nullable
    private AccessToken getCachedAccessToken() {
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        String jsonString = sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, null);

        AccessToken accessToken = null;

        if (!TextUtils.isEmpty(jsonString)) {
            accessToken = new Gson().fromJson(jsonString, AccessToken.class);
        }

        return accessToken;
    }
}
