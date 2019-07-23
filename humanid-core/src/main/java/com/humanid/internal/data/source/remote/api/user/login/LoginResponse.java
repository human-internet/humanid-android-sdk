package com.humanid.internal.data.source.remote.api.user.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("hash")
	private String hash;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("updatedAt")
	private String updatedAt;

	public LoginResponse(String hash, String createdAt, String updatedAt) {
		this.hash = hash;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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