package com.humanid.filmreview.domain.login.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.domain.base.RequestModel;

public class LoginRequest extends RequestModel {
    @SerializedName("exchangeToken")
    private String exchangeToken;

    public LoginRequest(final String exchangeToken) {
        this.exchangeToken = exchangeToken;
    }

    public String getExchangeToken() {
        return exchangeToken;
    }
}
