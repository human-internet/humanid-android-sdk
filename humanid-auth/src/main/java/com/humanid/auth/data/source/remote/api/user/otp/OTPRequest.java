package com.humanid.auth.data.source.remote.api.user.otp;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.Validate;

public class OTPRequest {

	@SerializedName("countryCode")
	private String countryCode;
	@SerializedName("phone")
	private String phone;
	@SerializedName("appId")
	private String applicationID;
	@SerializedName("appSecret")
	private String applicationSecret;

	public OTPRequest(@NonNull String countryCode, @NonNull String phone,
					  @NonNull String applicationID, @NonNull String applicationSecret) {
		Validate.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
		Validate.checkArgument(!TextUtils.isEmpty(phone), "phone");
		Validate.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");
		Validate.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");

		this.countryCode = countryCode;
		this.phone = phone;
		this.applicationID = applicationID;
		this.applicationSecret = applicationSecret;
	}

	@NonNull
	public String getCountryCode(){
		return countryCode;
	}

	@NonNull
	public String getPhone(){
		return phone;
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
		OTPRequest that = (OTPRequest) o;
		return countryCode.equals(that.countryCode) &&
				phone.equals(that.phone) &&
				applicationID.equals(that.applicationID) &&
				applicationSecret.equals(that.applicationSecret);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = 31 * result + (phone == null ? 0 : phone.hashCode());
		result = 31 * result + (applicationID == null ? 0 : applicationID.hashCode());
		result = 31 * result + (applicationSecret == null ? 0 : applicationSecret.hashCode());

		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "OTPRequest{" +
				"countryCode='" + countryCode + '\'' +
				", phone='" + phone + '\'' +
				", applicationID='" + applicationID + '\'' +
				", applicationSecret='" + applicationSecret + '\'' +
				'}';
	}
}
