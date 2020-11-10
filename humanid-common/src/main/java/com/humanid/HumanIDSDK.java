package com.humanid;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humanid.util.Preconditions;

public final class HumanIDSDK {

    private final static String TAG = HumanIDSDK.class.getSimpleName();

    private static HumanIDSDK INSTANCE;
    private final Context applicationContext;
    private final HumanIDOptions options;

    private HumanIDSDK(@NonNull Context applicationContext, @NonNull HumanIDOptions options) {
        this.applicationContext = applicationContext;
        this.options = options;
    }

    @NonNull
    public static HumanIDSDK getInstance() {
        synchronized (HumanIDSDK.class) {
            Preconditions.checkState(INSTANCE != null, "The SDK has not been initialized,"
                    + " make sure to call HumanIDSDK.initialize(context) first.");

            return INSTANCE;
        }
    }

    @Nullable
    public static HumanIDSDK initialize(@NonNull Context context) {
        synchronized (HumanIDSDK.class) {
            if (INSTANCE != null) {
                return getInstance();
            }

            HumanIDOptions options = HumanIDOptions.fromResource(context);

            if (options == null) {
                Log.w(TAG, "HumanIDSDK failed to initialize");
                return null;
            }

            return initialize(context, options);
        }
    }

    @NonNull
    private static HumanIDSDK initialize(@NonNull Context context, @NonNull HumanIDOptions options) {
        final HumanIDSDK humanIDSDK;
        Context applicationContext;

        if (context.getApplicationContext() == null) {
            // In shared processes' content providers getApplicationContext() can return null.
            applicationContext = context;
        } else {
            applicationContext = context.getApplicationContext();
        }

        synchronized (HumanIDSDK.class) {
            Preconditions.checkState(INSTANCE == null, "The SDK has been initialized.");
            Preconditions.checkState(applicationContext != null, "Application context cannot be null.");

            humanIDSDK = new HumanIDSDK(applicationContext, options);

            INSTANCE = humanIDSDK;
        }

        return humanIDSDK;
    }

    @NonNull
    public Context getApplicationContext() {
        return applicationContext;
    }

    @NonNull
    public HumanIDOptions getOptions() {
        return options;
    }
}
