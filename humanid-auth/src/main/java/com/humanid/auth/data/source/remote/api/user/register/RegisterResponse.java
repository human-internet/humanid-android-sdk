package com.humanid.auth.data.source.remote.api.user.register;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.Validate;

public class RegisterResponse {

	@SerializedName("hash")
	private String userHash;

	public RegisterResponse() {
		// Default Constructor
	}

	public RegisterResponse(@NonNull String userHash) {
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
