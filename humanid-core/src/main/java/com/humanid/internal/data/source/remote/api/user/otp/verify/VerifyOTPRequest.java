package com.humanid.internal.data.source.remote.api.user.otp.verify;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.humanid.internal.data.source.remote.api.BaseRequest;

public class VerifyOTPRequest extends BaseRequest {

	@SerializedName("countryCode")
	@Expose
	private String countryCode;

	@SerializedName("phone")
	@Expose
	private String phone;

	public VerifyOTPRequest(@NonNull String countryCode, @NonNull String phone,
							@NonNull String appID, @NonNull String appSecret) {
		super(appID, appSecret);
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
