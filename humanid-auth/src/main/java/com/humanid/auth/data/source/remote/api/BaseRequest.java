package com.humanid.auth.data.source.remote.api;

import com.google.gson.annotations.SerializedName;
import com.humanid.HumanIDSDK;

public abstract class BaseRequest {

    @SerializedName("appId")
    private String applicationID;

    @SerializedName("appSecret")
    private String applicationSecret;

    public BaseRequest() {
        this.applicationID = HumanIDSDK.getInstance().getOptions().getApplicationID();
        this.applicationSecret = HumanIDSDK.getInstance().getOptions().getApplicationSecret();
    }

    public String getApplicationID(){
        return applicationID;
    }

    public String getApplicationSecret() {
        return applicationSecret;
    }
}
