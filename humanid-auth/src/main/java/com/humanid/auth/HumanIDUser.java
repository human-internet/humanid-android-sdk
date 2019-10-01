package com.humanid.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.humanid.util.Preconditions;

public class HumanIDUser implements Parcelable {

    private String userHash;

    public HumanIDUser(@NonNull String userHash) {
        Preconditions.checkArgument(!TextUtils.isEmpty(userHash), "userHash");

        this.userHash = userHash;
    }

    private HumanIDUser(Parcel in) {
        userHash = in.readString();
    }

    public static final Creator<HumanIDUser> CREATOR = new Creator<HumanIDUser>() {
        @Override
        public HumanIDUser createFromParcel(Parcel in) {
            return new HumanIDUser(in);
        }

        @Override
        public HumanIDUser[] newArray(int size) {
            return new HumanIDUser[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userHash);
    }
}
