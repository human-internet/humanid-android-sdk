package com.humanid.auth.data.source.remote.api.user.revoke;

import com.google.gson.annotations.SerializedName;

public class RevokeAccessRequest {
    /**

     ID of the application sending the RevokeAccessRequest.
     */
    @SerializedName("appId")
    private String appId;

    /**

     String containing secret information passed from a HumanIDSDK instance.
     */
    @SerializedName("appSecret")
    private String appSecret;

    /**
     * The user specific hash of the request.
     */
    @SerializedName("userHash")
    private String userHash;

    /**
     * Constructor.
     * @param appId : the value that this.appId will be set to.
     * @param appSecret : the value that this.appId will be set to.
     * @param userHash : the value that this.userHash will be set to.
     */
    public RevokeAccessRequest(final String appId, final String appSecret, final String userHash) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.userHash = userHash;
    }

    /**
     *
     * @return : Returns String of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     *
     * @param appId : AppId to set.
     */
    public void setAppId(final String appId) {
        this.appId = appId;
    }

    /**
     *
     * @return : Returns appSecret.
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     *
     * @param appSecret : String to set appSecret.
     */
    public void setAppSecret(final String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     *
     * @return : Returns userHash.
     */
    public String getUserHash() {
        return userHash;
    }

    /**
     *
     * @param userHash : String to set userHash.
     */
    public void setUserHash(final String userHash) {
        this.userHash = userHash;
    }
}
