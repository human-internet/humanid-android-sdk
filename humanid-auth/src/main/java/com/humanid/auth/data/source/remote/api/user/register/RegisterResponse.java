package com.humanid.auth.data.source.remote.api.user.register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("data")
    private RegisterItem registerItem;

    public RegisterItem getRegisterItem() {
        return registerItem;
    }
}
