package com.humanid.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.humanid.util.Preconditions;

/**
 * This is a demo of a documented class
 */
public class HumanIDUser implements Parcelable {

    /**
     * Java string used for identifying and comparing user hashes.
     */
    private String exchangeToken;

    /**
     * Pre Checks String parameter exchangeToken and assigns it to the exchangeToken member variable.
     * @param exchangeToken String
     */
    public HumanIDUser(@NonNull String exchangeToken) {
        Preconditions.checkArgument(!TextUtils.isEmpty(exchangeToken), "exchangeToken");

        this.exchangeToken = exchangeToken;
    }

    private HumanIDUser(Parcel in) {
        exchangeToken = in.readString();
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
    public String getExchangeToken() {
        return exchangeToken;
    }

    /**
     * Returns boolean indicating if object o’s exchange token equals member variable exchangeToken
     * @param o Object -can cast to HumanIDUser
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanIDUser that = (HumanIDUser) o;
        return exchangeToken.equals(that.exchangeToken);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (exchangeToken == null ? 0 : exchangeToken.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "HumanIDUser{" +
                "userHash='" + exchangeToken + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exchangeToken);
    }
}
