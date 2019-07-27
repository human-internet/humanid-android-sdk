package com.humanid.data.repositories.auth.source.remote.api.login.check;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CheckLoginResponse {

	@SerializedName("message")
	private String message;

	public CheckLoginResponse() {
		// Default constructor
	}

	@NonNull
	public String getMessage(){
		return message;
	}
}