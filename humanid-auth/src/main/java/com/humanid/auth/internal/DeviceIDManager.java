package com.humanid.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DeviceIDManager {

    /**
     * Java string with value DeviceIDManager.
     */
    private final static String TAG = DeviceIDManager.class.getSimpleName();

    /**
     * Java string with value com.humanid.auth.internal.DeviceIDManager.
     */
    private final static String SHARED_PREF_NAME = DeviceIDManager.class.getCanonicalName();

    /**
     * Java string with value DEVICE_ID.
     */
    private final static String DEVICE_ID_KEY = "DEVICE_ID";

    /**
     * Java string with value NOTIFICATION_ID.
     */
    private final static String NOTIFICATION_ID_KEY = "NOTIFICATION_ID";

    /**
     * Instance of DeviceIDManager object. Used in getInstance().
     */
    private static volatile DeviceIDManager INSTANCE;

    /**
     * Android application context.
     */
    private final Context applicationContext;

    /**
     * Android shared preferences.
     */
    private final SharedPreferences sharedPreferences;

    /**
     * Constructor.
     * @param applicationContext : Application context object.
     * @param sharedPreferences : SharedPreference object.
     */
    private DeviceIDManager(@NonNull Context applicationContext,
                            @NonNull SharedPreferences sharedPreferences) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = sharedPreferences;
        setDeviceID(generateDeviceID());
    }

    /**
     * If this.INSTANCE is null, this.INSTANCE is set to a new DeviceIDManager object
     * @param context : Application context
     * @return : The current instance of an object of DeviceIDManager type, or a new instance if one doesn’t exist
     */
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

    /**
     *
     * @return : Returns DeviceID from sharedPreferences, returns generateDeviceID() if it doesn’t exist in sharedPreferences.
     */
    @NonNull
    public String getDeviceID() {
        String deviceID = sharedPreferences.getString(DEVICE_ID_KEY, null);
        if (deviceID == null) {
            deviceID = generateDeviceID();
        }
        return deviceID;
    }

    /**
     * Changes value of key DEVICE_ID_KEY in sharedPreferences to deviceID.
     * @param deviceID : The String that the device ID in sharedPreferences will be set to.
     */
    public void setDeviceID(@Nullable String deviceID) {
        sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceID).apply();
    }

    /**
     *
     * @return : <p>Returns NotificationID from sharedPreferences. If null, returns empty string.</p>
     */
    @NonNull
    public String getNotificationID() {
        String notificationID = sharedPreferences.getString(NOTIFICATION_ID_KEY, null);
        if (notificationID == null) notificationID = "";
        return notificationID;
    }

    /**
     * Changes value of key NOTIFICATION_ID_KEY in sharedPreferences to notificationID.
     * @param notificationID : The value that the notificationID in sharedPreferences will be set to.
     */
    public void setNotificationID(@Nullable String notificationID) {
        sharedPreferences.edit().putString(NOTIFICATION_ID_KEY, notificationID).apply();
    }

    /**
     * Clears the keys DEVICE_ID_KEY and NOTIFICATION_ID_KEY in sharedPreferences.
     */
    public void clear() {
        sharedPreferences.edit().remove(DEVICE_ID_KEY).apply();
        sharedPreferences.edit().remove(NOTIFICATION_ID_KEY).apply();
    }

    /**
     *
     * @return : Returns the value from DeviceIDUtils.getDeviceId(applicationContext).
     */
    @NonNull
    private String generateDeviceID() {
        return DeviceIDUtils.getDeviceId(applicationContext);
    }
}
