package com.humanid.auth.data.source.remote.api.user.login;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

	@SerializedName("countryCode")
	private String countryCode;
	@SerializedName("phone")
	private String phone;
	@SerializedName("verificationCode")
	private String verificationCode;
	@SerializedName("deviceId")
	private String deviceID;

	public LoginRequest(@NonNull String countryCode, @NonNull String phone,
						   @NonNull String verificationCode, @NonNull String deviceID) {
		this.countryCode = countryCode;
		this.phone = phone;
		this.verificationCode = verificationCode;
		this.deviceID = deviceID;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginRequest that = (LoginRequest) o;
		return countryCode.equals(that.countryCode) &&
				phone.equals(that.phone) &&
				verificationCode.equals(that.verificationCode) &&
				deviceID.equals(that.deviceID);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = 31 * result + (phone == null ? 0 : phone.hashCode());
		result = 31 * result + (verificationCode == null ? 0 : verificationCode.hashCode());
		result = 31 * result + (deviceID == null ? 0 : deviceID.hashCode());

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
				'}';
	}
}
