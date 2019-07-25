package com.humanid.auth.data.source.remote.api.register;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.humanid.auth.data.source.remote.api.BaseRequest;

public class RegisterRequest extends BaseRequest {

	@SerializedName("countryCode")
	@Expose
	private String countryCode;

	@SerializedName("phone")
	@Expose
	private String phone;

	@SerializedName("verificationCode")
	@Expose
	private String verificationCode;

	@SerializedName("deviceId")
	@Expose
	private String deviceID;

	@SerializedName("notifId")
	@Expose
	private String notificationID;

	public RegisterRequest(@NonNull String countryCode, @NonNull String phone,
						   @NonNull String verificationCode, @NonNull String deviceID,
						   @NonNull String notificationID) {
		super();
		this.countryCode = countryCode;
		this.phone = phone;
		this.verificationCode = verificationCode;
		this.deviceID = deviceID;
		this.notificationID = notificationID;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getPhone() {
		return phone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public String getNotificationID() {
		return notificationID;
	}
}
