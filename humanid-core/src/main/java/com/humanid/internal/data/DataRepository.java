package com.humanid.internal.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.internal.Validate;
import com.humanid.internal.data.source.local.sharedpreference.AppSharedPreference;

public class DataRepository {

    private final static String TAG = DataRepository.class.getSimpleName();

    private static volatile DataRepository INSTANCE;

    private Context applicationContext;
    private final AppSharedPreference appSharedPreference;

    private DataRepository(@NonNull Context applicationContext) {
        Validate.sdkInitialized();
        this.applicationContext = applicationContext;
        appSharedPreference = AppSharedPreference.getInstance(applicationContext);
    }

    @NonNull
    public static DataRepository getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    public String getAppID() {
        return appSharedPreference.getString(AppSharedPreference.Key.APP_ID);
    }

    public void setAppID(@NonNull String appID) {
        appSharedPreference.put(AppSharedPreference.Key.APP_ID, appID);
    }

    public String getAppSecret() {
        return appSharedPreference.getString(AppSharedPreference.Key.APP_ID);
    }

    public void setAppSecret(@NonNull String appSecret) {
        appSharedPreference.put(AppSharedPreference.Key.APP_SECRET, appSecret);
    }

    public String getUserHash() {
        return appSharedPreference.getString(AppSharedPreference.Key.USER_HASH);
    }

    public void setUserHas(@NonNull String userHas) {
        appSharedPreference.put(AppSharedPreference.Key.APP_SECRET, userHas);
    }
}
