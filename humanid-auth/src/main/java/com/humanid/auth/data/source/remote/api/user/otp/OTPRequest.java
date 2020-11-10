package com.humanid.auth.data.source.remote.api.user.otp;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class OTPRequest {

	@SerializedName("countryCode")
	private String countryCode;
	@SerializedName("phone")
	private String phone;

	public OTPRequest(@NonNull String countryCode, @NonNull String phone) {
		this.countryCode = countryCode;
		this.phone = phone;
	}

	@NonNull
	public String getCountryCode(){
		return countryCode;
	}

	@NonNull
	public String getPhone(){
		return phone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OTPRequest that = (OTPRequest) o;
		return countryCode.equals(that.countryCode) &&
				phone.equals(that.phone);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = 31 * result + (phone == null ? 0 : phone.hashCode());

		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "OTPRequest{" +
				"countryCode='" + countryCode + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}
