package com.humanid.auth.data.source.remote.api.user.updatephone;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UpdatePhoneResponse {

    @SerializedName("message")
    private String message;

    public UpdatePhoneResponse() {
        // Default Constructor
    }

    @NonNull
    public String getMessage(){
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePhoneResponse that = (UpdatePhoneResponse) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (message == null ? 0 : message.hashCode());

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "UpdatePhoneResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
