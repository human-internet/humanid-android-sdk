package com.humanid.auth.data.source.remote.api.user.login;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

	/**
	 * String storing phone number without country code.
	 */
	@SerializedName("countryCode")
	private String countryCode;
	/**
	 * String storing country code of phone number.
	 */
	@SerializedName("phone")
	private String phone;
	/**
	 * String storing verification code.
	 */
	@SerializedName("verificationCode")
	private String verificationCode;
	/**
	 * String storing device id.
	 */
	@SerializedName("deviceId")
	private String deviceID;

	/**
	 * Constructor.
	 * @param countryCode : String
	 * @param phone : String
	 * @param verificationCode : String
	 * @param deviceID : String
	 */
	public LoginRequest(@NonNull String countryCode, @NonNull String phone,
						   @NonNull String verificationCode, @NonNull String deviceID) {
		this.countryCode = countryCode;
		this.phone = phone;
		this.verificationCode = verificationCode;
		this.deviceID = deviceID;
	}

	/**
	 *
	 * @return : Returns the country code of a phone number.
	 */
	@NonNull
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 *
	 * @return : Returns phone number without the country code.
	 */
	@NonNull
	public String getPhone() {
		return phone;
	}

	/**
	 *
	 * @return : Returns verification code.
	 */
	@NonNull
	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 *
	 * @return : Returns deviceID.
	 */
	@NonNull
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 *
	 * @param o : Object to compare with.
	 * @return : Compares Object o with self.
	 */
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

	/**
	 *
	 * @return : Returns a unique hash code using the unique variables in self.
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = 31 * result + (phone == null ? 0 : phone.hashCode());
		result = 31 * result + (verificationCode == null ? 0 : verificationCode.hashCode());
		result = 31 * result + (deviceID == null ? 0 : deviceID.hashCode());

		return result;
	}

	/**
	 *
	 * @return : Returns print-friendly representation of self.
	 */
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
