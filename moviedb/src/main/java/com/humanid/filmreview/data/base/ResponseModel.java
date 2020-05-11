package com.humanid.filmreview.data.base;

import com.google.gson.annotations.SerializedName;

public abstract class ResponseModel {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
