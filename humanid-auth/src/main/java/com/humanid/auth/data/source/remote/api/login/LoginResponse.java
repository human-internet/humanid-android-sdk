package com.humanid.auth.data.source.remote.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("hash")
	@Expose
	private String hash;

	@SerializedName("createdAt")
	@Expose
	private String createdAt;

	@SerializedName("updatedAt")
	@Expose
	private String updatedAt;

	public LoginResponse() {
		// Default constructor
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