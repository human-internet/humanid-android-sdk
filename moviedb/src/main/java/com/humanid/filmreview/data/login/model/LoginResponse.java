package com.humanid.filmreview.data.login.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.data.base.ResponseModel;

public class LoginResponse extends ResponseModel {
    @SerializedName("data")
    private LoginData data;

    public void setData(final LoginData data) {
        this.data = data;
    }

    public LoginData getData() {
        return data;
    }
}
