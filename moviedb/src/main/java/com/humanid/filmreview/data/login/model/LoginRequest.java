package com.humanid.filmreview.data.login.model;

import com.google.gson.annotations.SerializedName;
import com.humanid.filmreview.data.base.RequestModel;
import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("exchangeToken", getExchangeToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
