package com.humanid.internal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.humanid.HumanIDException;
import com.humanid.HumanIDSDK;
import com.humanid.HumanIDSDKNotInitializeException;

import java.util.Collection;

public class Validate {

    private final static String TAG = HumanIDSDK.class.getSimpleName();

    private static final String NO_INTERNET_PERMISSION_REASON =
            "No internet permissions granted for the app, please add " +
                    "<uses-permission android:name=\"android.permission.INTERNET\" /> " +
                    "to your AndroidManifest.xml.";

    public static void notNull(Object arg, String name) {
        if (arg == null) {
            throw new NullPointerException("Argument '" + name + "' cannot be null");
        }
    }

    public static <T> void notEmpty(Collection<T> container, String name) {
        if (container.isEmpty()) {
            throw new IllegalArgumentException("Container '" + name + "' cannot be empty");
        }
    }

    public static <T> void containsNoNulls(Collection<T> container, String name) {
        Validate.notNull(container, name);
        for (T item : container) {
            if (item == null) {
                throw new NullPointerException("Container '" + name +
                        "' cannot contain null values");
            }
        }
    }

    public static void containsNoNullOrEmpty(Collection<String> container, String name) {
        Validate.notNull(container, name);
        for (String item : container) {
            if (item == null) {
                throw new NullPointerException("Container '" + name +
                        "' cannot contain null values");
            }
            if (item.length() == 0) {
                throw new IllegalArgumentException("Container '" + name +
                        "' cannot contain empty values");
            }
        }
    }

    public static <T> void notEmptyAndContainsNoNulls(Collection<T> container, String name) {
        Validate.containsNoNulls(container, name);
        Validate.notEmpty(container, name);
    }

    public static void runningOnUiThread() {
        if (!Looper.getMainLooper().equals(Looper.myLooper())) {
            throw new HumanIDException("This method should be called from the UI thread");
        }
    }

    public static void notNullOrEmpty(String arg, String name) {
        if (TextUtils.isEmpty(arg)) {
            throw new IllegalArgumentException("Argument '" + name + "' cannot be null or empty");
        }
    }

    public static void oneOf(Object arg, String name, Object... values) {
        for (Object value : values) {
            if (value != null) {
                if (value.equals(arg)) {
                    return;
                }
            } else {
                if (arg == null) {
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Argument '" + name +
                "' was not one of the allowed values");
    }

    public static void sdkInitialized() {
        if (HumanIDSDK.getInstance() == null) {
            throw new HumanIDSDKNotInitializeException(
                    "The SDK has not been initialized, make sure to call " +
                            "HumanIDSDK.initialize(context) first.");
        }
    }

    public static String hasAppID() {
        String id = HumanIDSDK.getInstance().getOptions().getAppId();
        if (id == null) {
            throw new IllegalStateException("No App ID found, please set the App ID.");
        }
        return id;
    }

    public static String hasAppSecret() {
        String token = HumanIDSDK.getInstance().getOptions().getAppSecret();
        if (token == null) {
            throw new IllegalStateException("No App Secret found, please set the App Secret.");
        }
        return token;
    }

    public static void hasInternetPermissions(Context context) {
        Validate.hasInternetPermissions(context, true);
    }

    public static void hasInternetPermissions(Context context, boolean shouldThrow) {
        Validate.notNull(context, "context");
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_DENIED) {
            if (shouldThrow) {
                throw new IllegalStateException(NO_INTERNET_PERMISSION_REASON);
            } else {
                Log.w(TAG, NO_INTERNET_PERMISSION_REASON);
            }
        }
    }

    public static boolean hasWiFiPermission(Context context) {
        return hasPermission(context, Manifest.permission.ACCESS_WIFI_STATE);
    }

    public static boolean hasChangeWifiStatePermission(Context context) {
        return hasPermission(context, Manifest.permission.CHANGE_WIFI_STATE);
    }

    public static boolean hasLocationPermission(Context context) {
        return hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                || hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static boolean hasBluetoothPermission(Context context) {
        return hasPermission(context, Manifest.permission.BLUETOOTH)
                && hasPermission(context, Manifest.permission.BLUETOOTH_ADMIN);
    }

    public static boolean hasPermission(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
