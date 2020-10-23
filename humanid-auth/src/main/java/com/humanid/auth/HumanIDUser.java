package com.humanid.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.humanid.util.Preconditions;

public class HumanIDUser implements Parcelable {

    /**
     * Java string used for identifying and comparing user hashes.
     */
    private String exchangeToken;

    /**
     *Pre Checks String parameter exchangeToken and assigns it to the exchangeToken member variable.
     * @param exchangeToken : String representing member variable exchangeToken
     */
    public HumanIDUser(@NonNull String exchangeToken) {
        Preconditions.checkArgument(!TextUtils.isEmpty(exchangeToken), "exchangeToken");

        this.exchangeToken = exchangeToken;
    }

    /**
     * Constructor
     * @param in
     */
    private HumanIDUser(Parcel in) {
        exchangeToken = in.readString();
    }

    /**
     * Array of Users.
     */
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

    /**
     *
     * @return : Returns exchangeToken
     */
    @NonNull
    public String getExchangeToken() {
        return exchangeToken;
    }

    /**
     *
     * @param o : HumanID User object to use in comparison
     * @return : Returns boolean indicating if object o’s exchange token equals member variable exchangeToken.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanIDUser that = (HumanIDUser) o;
        return exchangeToken.equals(that.exchangeToken);
    }

    /**
     * Generates hash using exchange token.
     * @return : Returns hash.
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (exchangeToken == null ? 0 : exchangeToken.hashCode());

        return result;
    }

    /**
     *
     * @return : Returns exchangeToken in print-friendly format.
     */
    @NonNull
    @Override
    public String toString() {
        return "HumanIDUser{" +
                "userHash='" + exchangeToken + '\'' +
                '}';
    }

    /**
     *
     * @return : Returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes exchangeToken into dest object using writeString.
     * @param dest : destination object to call writeString
     * @param flags : int flag
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exchangeToken);
    }
}
