package com.humanid.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.humanid.HumanIDSDK;
import com.humanid.internal.Validate;

import java.util.UUID;

public class DeviceIdentifierManager {

    private final static String TAG = DeviceIdentifierManager.class.getSimpleName();

    private static final String SHARED_PREFERENCES_NAME = DeviceIdentifierManager.class.getCanonicalName();
    private final static String DEVICE_ID_KEY = "DEVICE_ID";
    private final static String NOTIFICATION_ID_KEY = "DEVICE_ID";

    private static volatile DeviceIdentifierManager INSTANCE;

    private final SharedPreferences sharedPreferences;

    private DeviceIdentifierManager() {
        this.sharedPreferences = HumanIDSDK.getInstance().getApplicationContext()
                .getSharedPreferences(DeviceIdentifierManager.SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE);
    }

    @NonNull
    public static DeviceIdentifierManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DeviceIdentifierManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeviceIdentifierManager();
                }
            }
        }

        return INSTANCE;
    }

    public String getDeviceID() {
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        return sharedPreferences.getString(DEVICE_ID_KEY, generateNewDeviceID());
    }

    public void setDeviceID(@NonNull String deviceID) {
        Validate.checkArgument(!TextUtils.isEmpty(deviceID), "deviceID");
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        if (deviceID.equals(getDeviceID())) return;

        sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceID).apply();
    }

    public String getNotificationID() {
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        return sharedPreferences.getString(NOTIFICATION_ID_KEY, generateNewNotificationID());
    }

    public void setNotificationID(@NonNull String notificationID) {
        Validate.checkArgument(!TextUtils.isEmpty(notificationID), "notificationID");
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        if (notificationID.equals(getDeviceID())) return;
        sharedPreferences.edit().putString(NOTIFICATION_ID_KEY, notificationID).apply();
    }

    public void clear() {
        Validate.checkState(sharedPreferences != null, "SharedPreferences cannot be null.");

        sharedPreferences.edit().remove(DEVICE_ID_KEY).apply();
        sharedPreferences.edit().remove(NOTIFICATION_ID_KEY).apply();
    }

    @NonNull
    private String generateNewDeviceID() {
        return UUID.randomUUID().toString();
    }

    @NonNull
    private String generateNewNotificationID() {
        return UUID.randomUUID().toString();
    }
}
