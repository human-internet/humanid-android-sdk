package com.humanid.auth.data.model;

import androidx.annotation.NonNull;

public class User {
    /**
     * String for calculating hash code.
     */
    private String userHash;

    /**
     * String for identifying and comparing user hashes.
     */
    private String exchangeToken;

    /**
     * Constructor.
     * @param userHash : String for calculating hash code.
     * @param exchangeToken : String for identifying and comparing user hashes.
     */
    public User(@NonNull String userHash, @NonNull String exchangeToken) {
        this.userHash = userHash;
        this.exchangeToken = exchangeToken;
    }

    /**
     *
     * @return : Returns userHash.
     */
    @NonNull
    public String getUserHash() {
        return userHash;
    }

    /**
     *
     * @return : Returns exchangeToken.
     */
    @NonNull
    public String getExchangeToken() {
        return exchangeToken;
    }

    /**
     * Compares current userHash with userHash from Object o.
     * @param o : userHash object that is use to compare.
     * @return : Returns result of comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return userHash.equals(that.userHash);
    }

    /**
     *
     * @return : Returns hash code of userHash.
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (userHash == null ? 0 : userHash.hashCode());

        return result;
    }

    /**
     *
     * @return : Returns print friendly string of object.

     */
    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "userHash='" + userHash + '\'' +
                "exchangeToken='" + exchangeToken +
                '}';
    }
}
