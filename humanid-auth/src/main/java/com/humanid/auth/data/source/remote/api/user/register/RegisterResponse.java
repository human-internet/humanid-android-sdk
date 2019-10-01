package com.humanid.auth.data.source.remote.api.user.register;

import androidx.annotation.NonNull;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegisterResponse that = (RegisterResponse) o;
		return userHash.equals(that.userHash);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (userHash == null ? 0 : userHash.hashCode());

		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "RegisterResponse{" +
				"userHash='" + userHash + '\'' +
				'}';
	}
}
