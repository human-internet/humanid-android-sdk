package com.humanid.auth.service;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.humanid.auth.internal.DeviceIDManager;

public class HumanIDFCMService extends FirebaseMessagingService {

    /**
     * Java string with value HumanIDFCMService.
     */
    private final static String TAG = HumanIDFCMService.class.getSimpleName();

    /**
     Calls super.onNewToken(s), then DeviceID.getInstance(this).setNotificationID(s).
     * @param s : String to set as Notification Id.
     */
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        DeviceIDManager.getInstance(this).setNotificationID(s);
    }
}
