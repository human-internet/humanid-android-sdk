package com.humanid.auth.data.source.remote.api.user.update;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UpdateResponse {

    @SerializedName("hash")
    private String userHash;

    public UpdateResponse() {
        // Default Constructor
    }

    @NonNull
    public String getUserHash(){
        return userHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateResponse that = (UpdateResponse) o;
        return userHash.equals(that.userHash);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (userHash == null ? 0 : userHash.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "UpdateResponse{" +
                "userHash='" + userHash + '\'' +
                '}';
    }
}
