package com.humanid.internal.data.source.remote.api.user.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

	@SerializedName("hash")
	@Expose
	private String hash;

	@SerializedName("createdAt")
	@Expose
	private String createdAt;

	@SerializedName("updatedAt")
	@Expose
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
