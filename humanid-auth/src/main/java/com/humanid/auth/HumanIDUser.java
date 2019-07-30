package com.humanid.auth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.humanid.internal.Validate;

public class HumanIDUser {

    private String userHash;

    public HumanIDUser(@NonNull String userHash) {
        Validate.checkArgument(!TextUtils.isEmpty(userHash), "userHash");

        this.userHash = userHash;
    }

    @NonNull
    public String getUserHash() {
        return userHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanIDUser that = (HumanIDUser) o;
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
        return "HumanIDUser{" +
                "userHash='" + userHash + '\'' +
                '}';
    }
}
