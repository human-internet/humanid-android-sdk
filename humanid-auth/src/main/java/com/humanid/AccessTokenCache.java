package com.humanid;

import android.content.Context;
import android.content.SharedPreferences;

import com.humanid.internal.Validate;

public class AccessTokenCache {

    private final static String TAG = AccessToken.class.getSimpleName();

    private final static String CACHED_ACCESS_TOKEN_KEY = "CACHED_ACCESS_TOKEN";

    private final SharedPreferences sharedPreferences;

    public AccessTokenCache() {
        this.sharedPreferences = HumanIDSDK.getInstance().getApplicationContext()
                .getSharedPreferences(AccessTokenManager.SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE);
    }

    public AccessToken load() {
        AccessToken accessToken = null;
        if (hasCachedAccessToken()) {
            accessToken = getCachedAccessToken();
        }

        return accessToken;
    }

    public void save(AccessToken accessToken) {
        Validate.checkNotNull(accessToken, "accessToken");

        sharedPreferences.edit()
                .putString(CACHED_ACCESS_TOKEN_KEY, accessToken.toJsonString())
                .apply();
    }

    public void clear() {
        sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
    }

    private boolean hasCachedAccessToken() {
        return sharedPreferences.contains(CACHED_ACCESS_TOKEN_KEY);
    }

    private AccessToken getCachedAccessToken() {
        String jsonString = sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, null);
        return AccessToken.toObject(jsonString);
    }
}
