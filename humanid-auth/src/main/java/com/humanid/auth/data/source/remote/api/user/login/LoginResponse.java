package com.humanid.auth.data.source.remote.api.user.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.Validate;

public class LoginResponse {

	@SerializedName("hash")
	private String userHash;

	public LoginResponse() {
		// Default constructor
	}

	public LoginResponse(@NonNull String userHash) {
		Validate.checkArgument(!TextUtils.isEmpty(userHash), "userHash");

		this.userHash = userHash;
	}

	@NonNull
	public String getUserHash(){
		return userHash;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginResponse that = (LoginResponse) o;
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
		return "LoginResponse{" +
				"userHash='" + userHash + '\'' +
				'}';
	}
}