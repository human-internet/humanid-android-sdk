package com.humanid.filmreview.data.login.model;

import com.google.gson.annotations.SerializedName;

public class LoginData{
    @SerializedName("token")
    private String token;

    public LoginData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
