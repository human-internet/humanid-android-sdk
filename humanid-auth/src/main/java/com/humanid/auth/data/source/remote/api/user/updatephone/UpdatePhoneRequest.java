package com.humanid.auth.data.source.remote.api.user.updatephone;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UpdatePhoneRequest {

    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("phone")
    private String phone;
    @SerializedName("verificationCode")
    private String verificationCode;
    @SerializedName("existingHash")
    private String userHash;
    @SerializedName("appId")
    private String applicationID;
    @SerializedName("appSecret")
    private String applicationSecret;

    public UpdatePhoneRequest(@NonNull String countryCode, @NonNull String phone,
                              @NonNull String verificationCode, @NonNull String userHash,
                              @NonNull String applicationID, @NonNull String applicationSecret) {
        this.countryCode = countryCode;
        this.phone = phone;
        this.verificationCode = verificationCode;
        this.userHash = userHash;
        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
    }

    @NonNull
    public String getCountryCode() {
        return countryCode;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    @NonNull
    public String getVerificationCode() {
        return verificationCode;
    }

    @NonNull
    public String getUserHash() {
        return userHash;
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
        UpdatePhoneRequest that = (UpdatePhoneRequest) o;
        return countryCode.equals(that.countryCode) &&
                phone.equals(that.phone) &&
                verificationCode.equals(that.verificationCode) &&
                userHash.equals(that.userHash) &&
                applicationID.equals(that.applicationID) &&
                applicationSecret.equals(that.applicationSecret);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
        result = 31 * result + (phone == null ? 0 : phone.hashCode());
        result = 31 * result + (verificationCode == null ? 0 : verificationCode.hashCode());
        result = 31 * result + (userHash == null ? 0 : userHash.hashCode());
        result = 31 * result + (applicationID == null ? 0 : applicationID.hashCode());
        result = 31 * result + (applicationSecret == null ? 0 : applicationSecret.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "UpdatePhoneRequest{" +
                "countryCode='" + countryCode + '\'' +
                ", phone='" + phone + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", userHash='" + userHash + '\'' +
                ", applicationID='" + applicationID + '\'' +
                ", applicationSecret='" + applicationSecret + '\'' +
                '}';
    }
}
