package com.humanid.data.repositories.auth.source.remote.api.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.data.repositories.auth.source.remote.api.BaseAuthRequest;
import com.humanid.internal.Validate;

public class LoginRequest extends BaseAuthRequest {

	@SerializedName("existingHash")
	private String existingUserHash;

	@SerializedName("notifId")
	private String notificationID;

	public LoginRequest(@NonNull String existingUserHash, @NonNull String notificationID) {
		super();

		Validate.checkArgument(!TextUtils.isEmpty(existingUserHash), "existingUserHash");
		Validate.checkArgument(!TextUtils.isEmpty(notificationID), "notificationID");

		this.existingUserHash = existingUserHash;
		this.notificationID = notificationID;
	}

	@NonNull
	public String getExistingUserHash(){
		return existingUserHash;
	}

	@NonNull
	public String getNotificationID(){
		return notificationID;
	}
}
