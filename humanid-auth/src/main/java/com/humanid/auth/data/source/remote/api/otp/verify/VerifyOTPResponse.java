package com.humanid.auth.data.source.remote.api.otp.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOTPResponse {

	@SerializedName("message")
	@Expose
	private String message;

	public VerifyOTPResponse() {
		// Default Constructor
	}

	public String getMessage(){
		return message;
	}
}
