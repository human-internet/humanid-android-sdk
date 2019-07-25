package com.humanid.auth.data.source.remote.api.otp;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.auth.data.source.remote.api.BaseAuthRequest;
import com.humanid.internal.Validate;

public class OTPRequest extends BaseAuthRequest {

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("phone")
	private String phone;

	public OTPRequest(@NonNull String countryCode, @NonNull String phone) {
		super();

		Validate.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
		Validate.checkArgument(!TextUtils.isEmpty(phone), "phone");

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
}
