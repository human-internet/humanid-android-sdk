package com.humanid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class APIResponse<T> {

    private boolean successful;
    private final T data;
    private final String errorMessage;

    public APIResponse(boolean successful, @Nullable T data, @Nullable String errorMessage) {
        this.successful = successful;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> APIResponse<T> create(@NonNull Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() == null || response.code() == 204) {
                return empty();
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
        return new APIResponse<>(true, data, null);
    }

    private static <T> APIResponse<T> empty() {
        return new APIResponse<>(true, null, null);
    }

    private static <T> APIResponse<T> error(String msg) {
        return new APIResponse<>(false, null, msg);
    }

    public boolean isSuccessful() {
        return successful;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }
}
