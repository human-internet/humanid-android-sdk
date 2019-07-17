package com.humanid;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.UserManagerCompat;
import android.util.Log;

public final class HumanIDSDK {

    private final static String TAG = HumanIDSDK.class.getSimpleName();

    private static HumanIDSDK INSTANCE;
    private Context applicationContext;
    private final HumanIDOptions options;

    @NonNull
    public Context getApplicationContext() {
        return applicationContext;
    }

    @NonNull
    public HumanIDOptions getOptions() {
        return options;
    }

    @NonNull
    public static HumanIDSDK getInstance() {
        synchronized (HumanIDSDK.class) {
            if (INSTANCE == null) {
                throw new IllegalStateException("HumanIDSDK is not initialized");
            }

            return INSTANCE;
        }
    }

    @Nullable
    public static HumanIDSDK initialize(@NonNull Context context) {
        synchronized (HumanIDSDK.class) {
            if (INSTANCE == null) {
                INSTANCE = getInstance();
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
    public static HumanIDSDK initialize(@NonNull Context context, @NonNull HumanIDOptions options) {
        final HumanIDSDK humanIDSDK;
        Context applicationContext;

        if (context.getApplicationContext() == null) {
            // In shared processes' content providers getApplicationContext() can return null.
            applicationContext = context;
        } else {
            applicationContext = context.getApplicationContext();
        }

        synchronized (HumanIDSDK.class) {
            humanIDSDK = new HumanIDSDK(applicationContext, options);
            INSTANCE = humanIDSDK;
        }

        humanIDSDK.initializeAll();

        return humanIDSDK;
    }

    protected HumanIDSDK(@NonNull Context applicationContext, @NonNull HumanIDOptions options) {
        this.applicationContext = applicationContext;
        this.options = options;
    }

    private void initializeAll() {
        boolean inDirectBoot = !UserManagerCompat.isUserUnlocked(applicationContext);
        if (inDirectBoot) {
            // Ensure that all are initialized once the user unlocks the phone.
            UserUnlockReceiver.ensureReceiverRegistered(applicationContext);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static class UserUnlockReceiver extends BroadcastReceiver {

        private static UserUnlockReceiver INSTANCE;
        private final Context applicationContext;

        public UserUnlockReceiver(Context applicationContext) {
            this.applicationContext = applicationContext;
        }

        private static void ensureReceiverRegistered(Context applicationContext) {
            if (INSTANCE == null) {
                INSTANCE = new UserUnlockReceiver(applicationContext);
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_USER_UNLOCKED);
                applicationContext.registerReceiver(INSTANCE, intentFilter);
            }
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            synchronized (HumanIDSDK.class) {
                HumanIDSDK.INSTANCE.initializeAll();
            }

            unregister();
        }

        public void unregister() {
            applicationContext.unregisterReceiver(this);
        }
    }
}
