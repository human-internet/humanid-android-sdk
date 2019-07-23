package com.humanid.internal.data.source.remote.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.humanid.HumanIDSDK;

public abstract class BaseRequest {

    @SerializedName("appId")
    @Expose
    private String appID;

    @SerializedName("appSecret")
    @Expose
    private String appSecret;

    public BaseRequest() {
        this.appID = HumanIDSDK.getInstance().getOptions().getAppId();
        this.appSecret = HumanIDSDK.getInstance().getOptions().getAppSecret();
    }

    public String getAppID(){
        return appID;
    }

    public void setAppSecret(String appSecret){
        this.appSecret = appSecret;
    }
}
