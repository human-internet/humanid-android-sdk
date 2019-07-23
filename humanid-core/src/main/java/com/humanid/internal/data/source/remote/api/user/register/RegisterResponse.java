package com.humanid.internal.data.source.remote.api.user.register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

	@SerializedName("hash")
	private String hash;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("updatedAt")
	private String updatedAt;

	public RegisterResponse() {
		// Default Constructor
	}

	public String getHash(){
		return hash;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}