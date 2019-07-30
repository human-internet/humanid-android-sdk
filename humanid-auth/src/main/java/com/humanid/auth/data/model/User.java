package com.humanid.auth.data.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.humanid.internal.Validate;

public class User {

    private String userHash;
    private String deviceID;
    private String notificationID;
    private String applicationID;
    private String applicationSecret;

    public User(@NonNull String userHash, @NonNull String deviceID,
                @NonNull String notificationID, @NonNull String applicationID,
                @NonNull String applicationSecret) {
        Validate.checkArgument(!TextUtils.isEmpty(userHash), "userHash");
        Validate.checkArgument(!TextUtils.isEmpty(deviceID), "deviceID");
        Validate.checkArgument(!TextUtils.isEmpty(notificationID), "notificationID");
        Validate.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");
        Validate.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");

        this.userHash = userHash;
        this.deviceID = deviceID;
        this.notificationID = notificationID;
        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
    }

    @NonNull
    public String getUserHash() {
        return userHash;
    }

    @NonNull
    public String getDeviceID() {
        return deviceID;
    }

    @NonNull
    public String getNotificationID() {
        return notificationID;
    }

    @NonNull
    public String getApplicationID() {
        return applicationID;
    }

    @NonNull
    public String getApplicationSecret() {
        return applicationSecret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return userHash.equals(that.userHash) &&
                deviceID.equals(that.deviceID) &&
                notificationID.equals(that.notificationID) &&
                applicationID.equals(that.applicationID) &&
                applicationSecret.equals(that.applicationSecret);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (userHash == null ? 0 : userHash.hashCode());
        result = 31 * result + (deviceID == null ? 0 : deviceID.hashCode());
        result = 31 * result + (notificationID == null ? 0 : notificationID.hashCode());
        result = 31 * result + (applicationID == null ? 0 : applicationID.hashCode());
        result = 31 * result + (applicationSecret == null ? 0 : applicationSecret.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "HumanIDUser{" +
                "userHash='" + userHash + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", notificationID='" + notificationID + '\'' +
                ", applicationID='" + applicationID + '\'' +
                ", applicationSecret='" + applicationSecret + '\'' +
                '}';
    }
}
