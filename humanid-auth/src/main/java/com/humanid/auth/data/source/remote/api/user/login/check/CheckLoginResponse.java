package com.humanid.auth.data.source.remote.api.user.login.check;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.humanid.util.Preconditions;

public class CheckLoginResponse {

	@SerializedName("message")
	private String message;

	public CheckLoginResponse() {
		// Default constructor
	}

	public CheckLoginResponse(@NonNull String message) {
		Preconditions.checkArgument(!TextUtils.isEmpty(message), "message");

		this.message = message;
	}

	@NonNull
	public String getMessage(){
		return message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CheckLoginResponse that = (CheckLoginResponse) o;
		return message.equals(that.message);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (message == null ? 0 : message.hashCode());

		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "CheckLoginResponse{" +
				"message='" + message + '\'' +
				'}';
	}
}