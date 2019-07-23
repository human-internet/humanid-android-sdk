package com.humanid.internal.data.source.remote.api;

import com.google.gson.annotations.SerializedName;

public abstract class BaseRequest {

    @SerializedName("appId")
    private String appId;

    @SerializedName("appSecret")
    private String appSecret;

    public BaseRequest(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId(){
        return appId;
    }

    public void setAppSecret(String appSecret){
        this.appSecret = appSecret;
    }
}
