package com.humanid.auth.data.source.remote.api.otp;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class OTPResponse {

	@SerializedName("message")
	private String message;

	public OTPResponse() {
		// Default Constructor
	}

	@NonNull
	public String getMessage(){
		return message;
	}
}
