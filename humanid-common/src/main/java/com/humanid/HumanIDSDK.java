package com.humanid;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humanid.util.Preconditions;

/**
 * Class initializing the SDK to start the app.
 */
public final class HumanIDSDK {
    /**
     *HumanIDSDK
     */
    private final static String TAG = HumanIDSDK.class.getSimpleName();
    /**
     *HumanIDSDK object, used in getInstance()
     */
    private static HumanIDSDK INSTANCE;
    /**
     *Context object from the Android system
     */
    private final Context applicationContext;
    /**
     *HumanIDOptions object containing application info on humanID
     */
    private final HumanIDOptions options;

    /**
     *Constructor.
     * @param applicationContext set to this.applicationContext
     * @param options set to this.options
     */
    private HumanIDSDK(@NonNull Context applicationContext, @NonNull HumanIDOptions options) {
        this.applicationContext = applicationContext;
        this.options = options;
    }

    /**
     * If this.INSTANCE is null, doesn't return. Else, returns this.INSTANCE
     * @return HumanIDSDK: this.INSTANCE, if this.INSTANCE is not null
     */
    @NonNull
    public static HumanIDSDK getInstance() {
        synchronized (HumanIDSDK.class) {
            Preconditions.checkState(INSTANCE != null, "The SDK has not been initialized,"
                    + " make sure to call HumanIDSDK.initialize(context) first.");

            return INSTANCE;
        }
    }

    /**
     *If this.INSTANCE is null, a new HumanIDSDK object is created this.applicationContext = context.
     * @param context Context object used for generating HumanIDOptions object and creating new HumanIDSDK, if applicable
     * @return HumanIDSDK: the new HumanIDObject, if INSTANCE is null. Else, returns INSTANCE
     */
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

    /**
     *If self.INSTANCE is null, self.INSTANCE is set to a new HumanIDSDK object with parameters (context, options).
     * @param context first param passed into new HumanIDSDK object
     * @param options second param passed into new HumanIDSDK object
     * @return HumanIDSDK: if applicable, the new HumanIDSDK instance created using the parameters
     */
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

    /**
     *Returns this.context
     * @return Context: this.context
     */
    @NonNull
    public Context getApplicationContext() {
        return applicationContext;
    }

    /**
     *Returns this.options
     * @return HumanIDOptions: this.options
     */
    @NonNull
    public HumanIDOptions getOptions() {
        return options;
    }
}
