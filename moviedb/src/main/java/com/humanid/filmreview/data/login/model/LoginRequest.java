package com.humanid.filmreview.data.login.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.data.base.RequestModel;

public class LoginRequest extends RequestModel {
    @SerializedName("exchangeToken")
    private String exchangeToken;

    public LoginRequest(final String exchangeToken) {
        this.exchangeToken = exchangeToken;
    }

    public String getExchangeToken() {
        return exchangeToken;
    }

    @Override
    protected String getJsonString() {
        return getGson().toJson(this);
    }
}
