package com.humanid.auth.data.source.remote.api.user.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    /**
     * Object storing a user hash and an exchange token, both used for logging in.
     */
    @SerializedName("data")
    private LoginItem mLoginItem;

    /**
     *
     * @return : Returns the current login item.
     */
    public LoginItem getLoginItem() {
        return mLoginItem;
    }
}
