package com.humanid.samples.auth.data;

import android.support.annotation.NonNull;

public class User {

    private String userID;

    public User(@NonNull String userID) {
        this.userID = userID;
    }

    @NonNull
    public String getUserID() {
        return userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (userID == null ? 0 : userID.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                '}';
    }
}
