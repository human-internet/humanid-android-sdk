package com.humanid.internal.data.source.remote.api.user.login;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.data.source.remote.api.BaseRequest;

public class LoginRequest extends BaseRequest {

	@SerializedName("existingHash")
	private String existingHash;

	@SerializedName("notifId")
	private String notifId;


	public LoginRequest(String appId, String appSecret, String existingHash, String notifId) {
		super(appId, appSecret);
		this.existingHash = existingHash;
		this.notifId = notifId;
	}

	public String getExistingHash(){
		return existingHash;
	}

	public String getNotifId(){
		return notifId;
	}
}