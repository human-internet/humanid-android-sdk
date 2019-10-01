package com.humanid.auth.data.source.remote.api.user.register;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

	@SerializedName("countryCode")
	private String countryCode;
	@SerializedName("phone")
	private String phone;
	@SerializedName("verificationCode")
	private String verificationCode;
	@SerializedName("deviceId")
	private String deviceID;
	@SerializedName("notifId")
	private String notificationID;
	@SerializedName("appId")
	private String applicationID;
	@SerializedName("appSecret")
	private String applicationSecret;

	public RegisterRequest(@NonNull String countryCode, @NonNull String phone,
						   @NonNull String verificationCode, @NonNull String deviceID,
						   @NonNull String notificationID, @NonNull String applicationID,
						   @NonNull String applicationSecret) {
		this.countryCode = countryCode;
		this.phone = phone;
		this.verificationCode = verificationCode;
		this.deviceID = deviceID;
		this.notificationID = notificationID;
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
	public String getDeviceID() {
		return deviceID;
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
		RegisterRequest that = (RegisterRequest) o;
		return countryCode.equals(that.countryCode) &&
				phone.equals(that.phone) &&
				verificationCode.equals(that.verificationCode) &&
				deviceID.equals(that.deviceID) &&
				notificationID.equals(that.notificationID) &&
				applicationID.equals(that.applicationID) &&
				applicationSecret.equals(that.applicationSecret);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = 31 * result + (phone == null ? 0 : phone.hashCode());
		result = 31 * result + (verificationCode == null ? 0 : verificationCode.hashCode());
		result = 31 * result + (deviceID == null ? 0 : deviceID.hashCode());
		result = 31 * result + (notificationID == null ? 0 : notificationID.hashCode());
		result = 31 * result + (applicationID == null ? 0 : applicationID.hashCode());
		result = 31 * result + (applicationSecret == null ? 0 : applicationSecret.hashCode());

		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "RegisterRequest{" +
				"countryCode='" + countryCode + '\'' +
				", phone='" + phone + '\'' +
				", verificationCode='" + verificationCode + '\'' +
				", deviceID='" + deviceID + '\'' +
				", notificationID='" + notificationID + '\'' +
				", applicationID='" + applicationID + '\'' +
				", applicationSecret='" + applicationSecret + '\'' +
				'}';
	}
}
