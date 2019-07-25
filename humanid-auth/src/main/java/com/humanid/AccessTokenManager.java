package com.humanid;

import android.support.annotation.Nullable;

import com.humanid.internal.Validate;

public class AccessTokenManager {

    private final static String TAG = AccessTokenManager.class.getSimpleName();

    public static final String SHARED_PREFERENCES_NAME = AccessToken.class.getCanonicalName();

    private static volatile AccessTokenManager INSTANCE;

    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;

    private AccessTokenManager(AccessTokenCache accessTokenCache) {
        Validate.checkNotNull(accessTokenCache, "accessTokenCache");

        this.accessTokenCache = accessTokenCache;
    }

    public static AccessTokenManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AccessTokenManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccessTokenManager(new AccessTokenCache());
                }
            }
        }

        return INSTANCE;
    }

    @Nullable
    public AccessToken getCurrentAccessToken() {
        return currentAccessToken;
    }

    public void loadCurrentAccessToken() {
        AccessToken accessToken = accessTokenCache.load();

        if (accessToken != null) {
            setCurrentAccessToken(accessToken, false);
        }
    }

    public void setCurrentAccessToken(AccessToken currentAccessToken) {
        setCurrentAccessToken(currentAccessToken, true);
    }

    private void setCurrentAccessToken(AccessToken currentAccessToken, boolean saveToCache) {
        this.currentAccessToken = currentAccessToken;

        if (saveToCache) {
            if (currentAccessToken != null) {
                accessTokenCache.save(currentAccessToken);
            } else {
                accessTokenCache.clear();
            }
        }
    }
}
