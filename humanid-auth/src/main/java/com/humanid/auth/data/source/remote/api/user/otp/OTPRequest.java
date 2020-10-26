package com.humanid.auth.data.source.remote.api.user.otp;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class OTPRequest {

	/**
	 * Contains country code from request response.
	 */
	@SerializedName("countryCode")
	private String countryCode;

	/**
	 * Contains phone number from requests response.
	 */
	@SerializedName("phone")
	private String phone;

	/**
	 * Constructor.
	 * @param countryCode : String
	 * @param phone : String
	 */
	public OTPRequest(@NonNull String countryCode, @NonNull String phone) {
		this.countryCode = countryCode;
		this.phone = phone;
	}

	/**
	 *
	 * @return : Return country code.
	 */
	@NonNull
	public String getCountryCode(){
		return countryCode;
	}

	/**
	 *
	 * @return : Return phone number.
	 */
	@NonNull
	public String getPhone(){
		return phone;
	}

	/**
	 *
	 * @param o : Object to be compared.
	 * @return : Return true if object o’s country code equals class country code, otherwise false.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OTPRequest that = (OTPRequest) o;
		return countryCode.equals(that.countryCode) &&
				phone.equals(that.phone);
	}

	/**
	 *
	 * @return : Create hash code.
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = 31 * result + (phone == null ? 0 : phone.hashCode());

		return result;
	}

	/**
	 *
	 * @return : Return country code and phone number
	 */
	@NonNull
	@Override
	public String toString() {
		return "OTPRequest{" +
				"countryCode='" + countryCode + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}
