package com.humanid.auth.data.source.remote.api.login;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.humanid.auth.data.source.remote.api.BaseRequest;

public class LoginRequest extends BaseRequest {

	@SerializedName("existingHash")
	@Expose
	private String existingHash;

	@SerializedName("notifId")
	@Expose
	private String notificationID;

	public LoginRequest(@NonNull String existingHash, @NonNull String notificationID) {
		super();
		this.existingHash = existingHash;
		this.notificationID = notificationID;
	}

	public String getExistingHash(){
		return existingHash;
	}

	public String getNotificationID(){
		return notificationID;
	}
}
