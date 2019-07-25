package com.humanid.auth.data.source.remote.api.login;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("hash")
	private String userHash;

	public LoginResponse() {
		// Default constructor
	}

	@NonNull
	public String getUserHash(){
		return userHash;
	}
}