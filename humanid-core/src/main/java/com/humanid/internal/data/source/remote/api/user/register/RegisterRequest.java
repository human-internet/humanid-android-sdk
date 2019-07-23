package com.humanid.internal.data.source.remote.api.user.register;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.humanid.internal.data.source.remote.api.BaseRequest;

public class RegisterRequest extends BaseRequest {

	@SerializedName("countryCode")
	@Expose
	private String countryCode;

	@SerializedName("phone")
	@Expose
	private String phone;

	@SerializedName("deviceId")
	@Expose
	private String deviceID;

	@SerializedName("verificationCode")
	@Expose
	private String verificationCode;

	@SerializedName("notifId")
	@Expose
	private String notificationID;

	public RegisterRequest(@NonNull String countryCode, @NonNull String phone,
						   @NonNull String deviceID, @NonNull String verificationCode,
						   @NonNull String notificationID) {
		super();
		this.countryCode = countryCode;
		this.phone = phone;
		this.deviceID = deviceID;
		this.verificationCode = verificationCode;
		this.notificationID = notificationID;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getPhone() {
		return phone;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public String getNotificationID() {
		return notificationID;
	}
}
