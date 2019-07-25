package com.humanid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.internal.Validate;

public class AccessTokenManager {

    private final static String TAG = AccessTokenManager.class.getSimpleName();

    static final String SHARED_PREFERENCES_NAME = AccessToken.class.getCanonicalName();

    private static volatile AccessTokenManager INSTANCE;

    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;

    private AccessTokenManager(@NonNull AccessTokenCache accessTokenCache) {
        Validate.checkNotNull(accessTokenCache, "AccessTokenCache cannot be null.");

        this.accessTokenCache = accessTokenCache;
    }

    @NonNull
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
        loadCurrentAccessToken();
        return currentAccessToken;
    }

    private void loadCurrentAccessToken() {
        Validate.checkState(accessTokenCache != null, "AccessTokenCache cannot be null.");

        AccessToken accessToken = accessTokenCache.load();

        if (accessToken == null) return;

        setCurrentAccessToken(accessToken, false);
    }

    public void setCurrentAccessToken(@Nullable AccessToken currentAccessToken) {
        setCurrentAccessToken(currentAccessToken, true);
    }

    private void setCurrentAccessToken(@Nullable AccessToken currentAccessToken, boolean saveToCache) {
        Validate.checkState(accessTokenCache != null, "AccessTokenCache cannot be null.");

        this.currentAccessToken = currentAccessToken;

        if (!saveToCache) return;

        if (currentAccessToken != null) {
            accessTokenCache.save(currentAccessToken);
        } else {
            accessTokenCache.clear();
        }
    }
}
