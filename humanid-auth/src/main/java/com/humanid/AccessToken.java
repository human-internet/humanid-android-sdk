package com.humanid;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.humanid.internal.Validate;

public final class AccessToken implements Parcelable {

    private String userHash;
    private String deviceID;
    private String notificationID;
    private String applicationID;
    private String applicationSecret;

    public AccessToken(String userHash, String deviceID, String notificationID,
                       String applicationID, String applicationSecret) {
        Validate.checkArgument(TextUtils.isEmpty(userHash), "userHash");
        Validate.checkArgument(TextUtils.isEmpty(deviceID), "deviceID");
        Validate.checkArgument(TextUtils.isEmpty(notificationID), "notificationID");
        Validate.checkArgument(TextUtils.isEmpty(applicationID), "applicationID");
        Validate.checkArgument(TextUtils.isEmpty(applicationSecret), "applicationSecret");

        this.userHash = userHash;
        this.deviceID = deviceID;
        this.notificationID = notificationID;
        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
    }

    public String getUserHash() {
        return userHash;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public String getApplicationSecret() {
        return applicationSecret;
    }

    @Nullable
    public String toJsonString() {
        String jsonString;

        try {
            jsonString = new Gson().toJson(this);
        } catch (Exception e) {
            jsonString = null;
        }

        return jsonString;
    }

    @Nullable
    public static AccessToken toObject(String jsonString) {
        AccessToken accessToken;

        if (jsonString == null) return null;

        try {
            accessToken = new Gson().fromJson(jsonString, AccessToken.class);
        } catch (Exception e) {
            accessToken = null;
        }

        return accessToken;
    }

    AccessToken(Parcel parcel) {
        this.userHash = parcel.readString();
        this.deviceID = parcel.readString();
        this.notificationID = parcel.readString();
        this.applicationID = parcel.readString();
        this.applicationSecret = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userHash);
        dest.writeString(deviceID);
        dest.writeString(notificationID);
        dest.writeString(applicationID);
        dest.writeString(applicationSecret);
    }

    public static final Parcelable.Creator<AccessToken> CREATOR = new Parcelable.Creator<AccessToken>() {

        @Override
        public AccessToken createFromParcel(Parcel source) {
            return new AccessToken(source);
        }

        @Override
        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    };
}
