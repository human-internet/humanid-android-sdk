package com.humanid.auth.data.source.remote.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.IOException;

import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Response;

public class APIResponse<T> {

    /**
     * Boolean representing if response api success field is true or false.
     */
    @SerializedName("success")
    private boolean successful;

    /**
     * String representing HTTP code from response api.
     */
    @SerializedName("code")
    private String code;

    /**
     * String representing message field from response api.
     */
    @SerializedName("message")
    private String message;

    /**
     * Generic type called data.
     */
    private final T data;

    /**
     * Constructor
     * @param successful : Boolean representing if response api success field is true or false.
     * @param data : Generic type called data.
     * @param code : String representing HTTP code from response api.
     * @param message : String representing message field from response api.
     */
    public APIResponse(boolean successful, @Nullable T data, @Nullable String code, @Nullable String message) {
        this.successful = successful;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    /**
     *
     * @param response : REST api response.
     * @param <T> :Dgeneric data type.
     * @return Returns response body as new Response api object if response is successful or response’s error message if not successful.
     */
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
                    String errorResponse = errorBody.string();
                    JSONObject err = new JSONObject(errorResponse);
                    error = err.optString("message");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            } else {
                error = response.message();
            }

            return error(error);
        }
    }

    /**
     *
     * @param t : Throwable object.
     * @return : Check if empty and return error message for the new api response.
     */
    public static <T> APIResponse<T> create(@NonNull Throwable t) {
        String errorMessage = t.getMessage();
        if (TextUtils.isEmpty(errorMessage)) errorMessage = "unknown error";
        return error(errorMessage);
    }

    /**
     * Turns T data object into new Response api object holding data.
     * @param data : Generic data type.
     * @return : new APIResponse object with data set using constructor.
     */
    private static <T> APIResponse<T> success(@NonNull T data) {
        return new APIResponse<>(true, data, null, null);
    }

    /**
     * Turns T data object into new Response api object with error message.
     * @param message : String containing api message.
     * @return : new APIResponse object with data null using constructor.
     */
    private static <T> APIResponse<T> empty(String message) {
        return new APIResponse<>(true, null, null, message);
    }

    private static <T> APIResponse<T> error(String msg) {

        return new APIResponse<>(false, null, null, msg);
    }

    /**
     *
     * @return : Returns boolean representing REST api success
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     *
     * @return : Returns generic type data
     */
    @Nullable
    public T getData() {
        return data;
    }

    /**
     *
     * @return : Returns HTTP code
     */
    @Nullable
    public String getCode() {
        return code;
    }

    /**
     *
     * @return : Returns api message
     */
    @Nullable
    public String getMessage() {
        return message;
    }
}
