package com.humanid.auth.data.source.remote.api.user.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    private LoginItem mLoginItem;

    public LoginItem getLoginItem() {
        return mLoginItem;
    }
}
