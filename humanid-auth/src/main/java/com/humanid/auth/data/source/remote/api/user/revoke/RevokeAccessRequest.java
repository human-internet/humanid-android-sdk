package com.humanid.auth.data.source.remote.api.user.revoke;

import com.google.gson.annotations.SerializedName;

public class RevokeAccessRequest {
    @SerializedName("appId")
    private String appId;

    @SerializedName("appSecret")
    private String appSecret;

    @SerializedName("userHash")
    private String userHash;

    public RevokeAccessRequest(final String appId, final String appSecret, final String userHash) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.userHash = userHash;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(final String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(final String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }
}
