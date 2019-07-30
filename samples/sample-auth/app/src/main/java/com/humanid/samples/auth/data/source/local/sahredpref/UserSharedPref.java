package com.humanid.samples.auth.data.source.local.sahredpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class UserSharedPref {

    private final static String TAG = UserSharedPref.class.getSimpleName();

    private final static String PREF_NAME = UserSharedPref.class.getCanonicalName();

    private static volatile UserSharedPref INSTANCE;

    private final Context applicationContext;
    private final SharedPreferences sharedPreferences;

    private UserSharedPref(@NonNull Context applicationContext,
                           @NonNull SharedPreferences sharedPreferences) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = sharedPreferences;
    }

    public static UserSharedPref getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (UserSharedPref.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();
                    SharedPreferences sharedPreferences = applicationContext
                            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

                    INSTANCE = new UserSharedPref(applicationContext, sharedPreferences);
                }
            }
        }

        return INSTANCE;
    }
}
