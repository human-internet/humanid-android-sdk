package com.humanid.internal.data.source.remote.api.user.otp.verify;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.data.source.remote.api.BaseRequest;

public class VerifyOTPRequest extends BaseRequest {

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("phone")
	private String phone;

	public VerifyOTPRequest(String appId, String appSecret, String countryCode, String phone) {
		super(appId, appSecret);
		this.countryCode = countryCode;
		this.phone = phone;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public String getPhone(){
		return phone;
	}
}