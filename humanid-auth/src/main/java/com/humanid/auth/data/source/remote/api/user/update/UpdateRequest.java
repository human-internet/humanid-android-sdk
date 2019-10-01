package com.humanid.auth.data.source.remote.api.user.update;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UpdateRequest {

    @SerializedName("hash")
    private String userHash;
    @SerializedName("notifId")
    private String notificationID;
    @SerializedName("appId")
    private String applicationID;
    @SerializedName("appSecret")
    private String applicationSecret;

    public UpdateRequest(@NonNull String userHash, @NonNull String notificationID,
                         @NonNull String applicationID, @NonNull String applicationSecret) {
        this.userHash = userHash;
        this.notificationID = notificationID;
        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
    }

    @NonNull
    public String getUserHash() {
        return userHash;
    }

    @NonNull
    public String getNotificationID() {
        return notificationID;
    }

    @NonNull
    public String getApplicationID(){
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
        UpdateRequest that = (UpdateRequest) o;
        return userHash.equals(that.userHash) &&
                notificationID.equals(that.notificationID) &&
                applicationID.equals(that.applicationID) &&
                applicationSecret.equals(that.applicationSecret);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (userHash == null ? 0 : userHash.hashCode());
        result = 31 * result + (notificationID == null ? 0 : notificationID.hashCode());
        result = 31 * result + (applicationID == null ? 0 : applicationID.hashCode());
        result = 31 * result + (applicationSecret == null ? 0 : applicationSecret.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "UpdateRequest{" +
                "userHash='" + userHash + '\'' +
                ", notificationID='" + notificationID + '\'' +
                ", applicationID='" + applicationID + '\'' +
                ", applicationSecret='" + applicationSecret + '\'' +
                '}';
    }
}
