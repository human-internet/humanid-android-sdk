package com.humanid.auth.service;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.humanid.auth.internal.DeviceIDManager;

public class HumanIDFCMService extends FirebaseMessagingService {

    private final static String TAG = HumanIDFCMService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        DeviceIDManager.getInstance(this).setNotificationID(s);
    }
}
