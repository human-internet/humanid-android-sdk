package com.humanid.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.UUID;

public class DeviceIDManager {

    private final static String TAG = DeviceIDManager.class.getSimpleName();

    private final static String SHARED_PREF_NAME = DeviceIDManager.class.getCanonicalName();
    private final static String DEVICE_ID_KEY = "DEVICE_ID";
    private final static String NOTIFICATION_ID_KEY = "NOTIFICATION_ID";

    private static volatile DeviceIDManager INSTANCE;

    private final Context applicationContext;
    private final SharedPreferences sharedPreferences;

    private DeviceIDManager(@NonNull Context applicationContext,
                            @NonNull SharedPreferences sharedPreferences) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    public static DeviceIDManager getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (DeviceIDManager.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();
                    SharedPreferences sharedPreferences = applicationContext
                            .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    INSTANCE = new DeviceIDManager(applicationContext, sharedPreferences);
                }
            }
        }

        return INSTANCE;
    }

    public String getDeviceID() {
        return sharedPreferences.getString(DEVICE_ID_KEY, generateNewDeviceID());
    }

    public void setDeviceID(@NonNull String deviceID) {
        Validate.checkArgument(!TextUtils.isEmpty(deviceID), "deviceID");

        if (deviceID.equals(getDeviceID())) return;

        sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceID).apply();
    }

    public String getNotificationID() {
        return sharedPreferences.getString(NOTIFICATION_ID_KEY, generateNewNotificationID());
    }

    public void setNotificationID(@NonNull String notificationID) {
        Validate.checkArgument(!TextUtils.isEmpty(notificationID), "notificationID");

        if (notificationID.equals(getDeviceID())) return;

        sharedPreferences.edit().putString(NOTIFICATION_ID_KEY, notificationID).apply();
    }

    public void clear() {
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
