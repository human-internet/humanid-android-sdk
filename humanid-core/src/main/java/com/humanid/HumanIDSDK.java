package com.humanid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.humanid.internal.Validate;

public final class HumanIDSDK {

    private final static String TAG = HumanIDSDK.class.getSimpleName();

    private static HumanIDSDK INSTANCE;
    private Context applicationContext;
    private final HumanIDOptions options;

    private HumanIDSDK(@NonNull Context applicationContext, @NonNull HumanIDOptions options) {
        this.applicationContext = applicationContext;
        this.options = options;
    }

    public static HumanIDSDK getInstance() {
        synchronized (HumanIDSDK.class) {
            Validate.checkState(INSTANCE == null, "The SDK has not been initialized,"
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
            Validate.checkState(INSTANCE != null, "The SDK has been initialized!");
            Validate.checkState(applicationContext != null, "Application context cannot be null.");

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
