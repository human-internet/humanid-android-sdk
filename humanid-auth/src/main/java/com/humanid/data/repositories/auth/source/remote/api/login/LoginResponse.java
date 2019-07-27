package com.humanid.data.repositories.auth.source.remote.api.login;

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