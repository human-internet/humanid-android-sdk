package com.humanid.auth.data.source.remote.api.user.register;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RegisterItem {

	@SerializedName("userHash")
	private String userHash;

	@SerializedName("exchangeToken")
	private String exchangeToken;

	public RegisterItem() {
		// Default Constructor
	}

	@NonNull
	public String getUserHash(){
		return userHash;
	}

	public String getExchangeToken() {
		return exchangeToken;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegisterItem that = (RegisterItem) o;
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
				" exchangeToken='" + exchangeToken +
				'}';
	}
}
