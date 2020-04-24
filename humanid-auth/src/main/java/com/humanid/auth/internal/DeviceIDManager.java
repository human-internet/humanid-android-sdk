package com.humanid.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        setDeviceID(generateDeviceID());
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

    @NonNull
    public String getDeviceID() {
        String deviceID = sharedPreferences.getString(DEVICE_ID_KEY, null);
        if (deviceID == null) {
            deviceID = generateDeviceID();
        }
        return deviceID;
    }

    public void setDeviceID(@Nullable String deviceID) {
        sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceID).apply();
    }

    @NonNull
    public String getNotificationID() {
        String notificationID = sharedPreferences.getString(NOTIFICATION_ID_KEY, null);
        if (notificationID == null) notificationID = "";
        return notificationID;
    }

    public void setNotificationID(@Nullable String notificationID) {
        sharedPreferences.edit().putString(NOTIFICATION_ID_KEY, notificationID).apply();
    }

    public void clear() {
        sharedPreferences.edit().remove(DEVICE_ID_KEY).apply();
        sharedPreferences.edit().remove(NOTIFICATION_ID_KEY).apply();
    }

    @NonNull
    private String generateDeviceID() {
        return DeviceIDUtils.getPseudoDeviceID(applicationContext);
    }
}
