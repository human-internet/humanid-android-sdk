package com.humanid.auth.data.source.remote.api.user.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.humanid.internal.Validate;

public class LoginRequest {

	@SerializedName("existingHash")
	private String existingUserHash;
	@SerializedName("notifId")
	private String notificationID;
	@SerializedName("appId")
	private String applicationID;
	@SerializedName("appSecret")
	private String applicationSecret;

	public LoginRequest(@NonNull String existingUserHash, @NonNull String notificationID,
						@NonNull String applicationID, @NonNull String applicationSecret) {
		Validate.checkArgument(!TextUtils.isEmpty(existingUserHash), "existingUserHash");
		Validate.checkArgument(!TextUtils.isEmpty(notificationID), "notificationID");
		Validate.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");
		Validate.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");

		this.existingUserHash = existingUserHash;
		this.notificationID = notificationID;
		this.applicationID = applicationID;
		this.applicationSecret = applicationSecret;
	}

	@NonNull
	public String getExistingUserHash(){
		return existingUserHash;
	}

	@NonNull
	public String getNotificationID(){
		return notificationID;
	}

	@NonNull
	public String getApplicationID(){
		return applicationID;
	}

	@NonNull
	public String getApplicationSecret() {
		return applicationSecret;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginRequest that = (LoginRequest) o;
		return existingUserHash.equals(that.existingUserHash) &&
				notificationID.equals(that.notificationID) &&
				applicationID.equals(that.applicationID) &&
				applicationSecret.equals(that.applicationSecret);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (existingUserHash == null ? 0 : existingUserHash.hashCode());
		result = 31 * result + (notificationID == null ? 0 : notificationID.hashCode());
		result = 31 * result + (applicationID == null ? 0 : applicationID.hashCode());
		result = 31 * result + (applicationSecret == null ? 0 : applicationSecret.hashCode());

		return result;
	}

	@NonNull
	@Override
	public String toString() {
		return "LoginRequest{" +
				"existingUserHash='" + existingUserHash + '\'' +
				", notificationID='" + notificationID + '\'' +
				", applicationID='" + applicationID + '\'' +
				", applicationSecret='" + applicationSecret + '\'' +
				'}';
	}
}
