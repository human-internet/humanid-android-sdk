package com.humanid.auth.data.source.remote.api.user.login;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginItem {

	/**
	 * String holding user hash for login.
	 */
	@SerializedName("userHash")
	private String userHash;

	/**
	 * String holding exchange token for login.
	 */
	@SerializedName("exchangeToken")
	private String exchangeToken;

	/**
	 * Empty constructor.
	 */
	public LoginItem() {
		// Default Constructor
	}

	/**
	 *
	 * @return : Returns user hash.
	 */
	@NonNull
	public String getUserHash(){
		return userHash;
	}

	/**
	 *
	 * @return : Returns exchange token.
	 */
	public String getExchangeToken() {
		return exchangeToken;
	}

	/**
	 *
	 * @param o : Object to be compared
 	 * @return : Returns true if object user hash is equal to class user hash.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginItem that = (LoginItem) o;
		return userHash.equals(that.userHash);
	}

	/**
	 *
	 * @return : Create hash code.
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (userHash == null ? 0 : userHash.hashCode());

		return result;
	}

	/**
	 *
	 * @return : Return print friendly string for user hash and exchange token.
	 */
	@NonNull
	@Override
	public String toString() {
		return "RegisterResponse{" +
				"userHash='" + userHash + '\'' +
				" exchangeToken='" + exchangeToken +
				'}';
	}
}
