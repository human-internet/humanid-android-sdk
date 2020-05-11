package com.humanid.auth.data.source.remote.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class APIResponse<T> {

    @SerializedName("success")
    private boolean successful;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    private final T data;

    public APIResponse(boolean successful, @Nullable T data, @Nullable String code, @Nullable String message) {
        this.successful = successful;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> APIResponse<T> create(@NonNull Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() == null || response.code() == 204) {
                return empty("No Data");
            } else {
                return success(response.body());
            }
        } else {
            String error = "unknown error";

            ResponseBody errorBody = response.errorBody();

            if (errorBody != null) {
                try {
                    error = errorBody.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                error = response.message();
            }

            return error(error);
        }
    }

    public static <T> APIResponse<T> create(@NonNull Throwable t) {
        String errorMessage = t.getMessage();
        if (TextUtils.isEmpty(errorMessage)) errorMessage = "unknown error";
        return error(errorMessage);
    }

    private static <T> APIResponse<T> success(@NonNull T data) {
        return new APIResponse<>(true, data, null, null);
    }

    private static <T> APIResponse<T> empty(String message) {
        return new APIResponse<>(true, null, null, message);
    }

    private static <T> APIResponse<T> error(String msg) {

        return new APIResponse<>(false, null, null, msg);
    }

    public boolean isSuccessful() {
        return successful;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
