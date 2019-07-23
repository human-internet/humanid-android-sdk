package com.humanid.internal.data.source.remote.api.user.register;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.data.source.remote.api.BaseRequest;

public class RegisterRequest extends BaseRequest {

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("phone")
	private String phone;

	@SerializedName("verificationCode")
	private String verificationCode;

	@SerializedName("deviceId")
	private String deviceId;

	@SerializedName("notifId")
	private String notifId;

	public RegisterRequest(String appId, String appSecret, String countryCode, String phone,
						   String verificationCode, String deviceId, String notifId) {
		super(appId, appSecret);
		this.countryCode = countryCode;
		this.phone = phone;
		this.verificationCode = verificationCode;
		this.deviceId = deviceId;
		this.notifId = notifId;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public String getPhone(){
		return phone;
	}

	public String getVerificationCode(){
		return verificationCode;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public String getNotifId(){
		return notifId;
	}
}