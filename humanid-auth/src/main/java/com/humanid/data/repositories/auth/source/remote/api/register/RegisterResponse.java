package com.humanid.data.repositories.auth.source.remote.api.register;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

	@SerializedName("hash")
	private String userHash;

	public RegisterResponse() {
		// Default Constructor
	}

	@NonNull
	public String getUserHash(){
		return userHash;
	}
}
