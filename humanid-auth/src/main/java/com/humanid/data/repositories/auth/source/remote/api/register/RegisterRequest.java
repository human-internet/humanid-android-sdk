package com.humanid.data.repositories.auth.source.remote.api.register;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.data.repositories.auth.source.remote.api.BaseAuthRequest;
import com.humanid.internal.Validate;

public class RegisterRequest extends BaseAuthRequest {

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("phone")
	private String phone;

	@SerializedName("verificationCode")
	private String verificationCode;

	@SerializedName("deviceId")
	private String deviceID;

	@SerializedName("notifId")
	private String notificationID;

	public RegisterRequest(@NonNull String countryCode, @NonNull String phone,
                           @NonNull String verificationCode, @NonNull String deviceID,
                           @NonNull String notificationID) {
		super();

		Validate.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
		Validate.checkArgument(!TextUtils.isEmpty(phone), "phone");
		Validate.checkArgument(!TextUtils.isEmpty(verificationCode), "verificationCode");
		Validate.checkArgument(!TextUtils.isEmpty(deviceID), "deviceID");
		Validate.checkArgument(!TextUtils.isEmpty(notificationID), "notificationID");

		this.countryCode = countryCode;
		this.phone = phone;
		this.verificationCode = verificationCode;
		this.deviceID = deviceID;
		this.notificationID = notificationID;
	}

	@NonNull
	public String getCountryCode() {
		return countryCode;
	}

	@NonNull
	public String getPhone() {
		return phone;
	}

	@NonNull
	public String getVerificationCode() {
		return verificationCode;
	}

	@NonNull
	public String getDeviceID() {
		return deviceID;
	}

	@NonNull
	public String getNotificationID() {
		return notificationID;
	}
}
