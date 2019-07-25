package com.humanid.auth.data.source.remote.api;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.humanid.HumanIDSDK;

public abstract class BaseAuthRequest {

    @SerializedName("appId")
    private String applicationID;

    @SerializedName("appSecret")
    private String applicationSecret;

    public BaseAuthRequest() {
        this.applicationID = HumanIDSDK.getInstance().getOptions().getApplicationID();
        this.applicationSecret = HumanIDSDK.getInstance().getOptions().getApplicationSecret();
    }

    @NonNull
    public String getApplicationID(){
        return applicationID;
    }

    @NonNull
    public String getApplicationSecret() {
        return applicationSecret;
    }
}
