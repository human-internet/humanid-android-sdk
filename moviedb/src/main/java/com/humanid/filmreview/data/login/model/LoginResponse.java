package com.humanid.filmreview.domain.login.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.domain.base.ResponseModel;

public class LoginResponse extends ResponseModel {
    @SerializedName("data")
    private LoginData data;

    public LoginResponse(final LoginData data) {
        this.data = data;
    }

    public LoginData getData() {
        return data;
    }
}
