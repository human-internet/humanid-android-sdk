package com.humanid.internal.data.source.remote.api.user.otp.verify;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPResponse {

	@SerializedName("message")
	private String message;

	public VerifyOTPResponse() {
		// Default Constructor
	}

	public String getMessage(){
		return message;
	}
}