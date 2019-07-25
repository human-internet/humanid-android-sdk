package com.humanid.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.humanid.internal.Validate;

public class HumanIDUser implements Parcelable {

    private String userHash;

    public HumanIDUser(@NonNull String userHash) {
        Validate.checkArgument(!TextUtils.isEmpty(userHash), "userHash");

        this.userHash = userHash;
    }

    @NonNull
    public String getUserHash() {
        return userHash;
    }

    private HumanIDUser(Parcel parcel) {
        this.userHash = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userHash);
    }

    public static final Parcelable.Creator<HumanIDUser> CREATOR = new Parcelable.Creator<HumanIDUser>() {

        @Override
        public HumanIDUser createFromParcel(Parcel source) {
            return new HumanIDUser(source);
        }

        @Override
        public HumanIDUser[] newArray(int size) {
            return new HumanIDUser[size];
        }
    };
}
