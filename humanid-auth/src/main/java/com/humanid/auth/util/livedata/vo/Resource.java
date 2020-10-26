package com.humanid.auth.util.livedata.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * The Resource class stores information about the status of some data
 */
public class Resource<T> {
    /**
     * The Status of the class, either LOADING, SUCCESS, or ERROR
     */
    @NonNull
    public final Status status;
    /**
     * The data associated with the status
     */
    @Nullable
    public final T data;
    /**
     * Error message, if applicable
     */
    @Nullable
    public final String message;

    /**
     *Constructor
     * @param status ENUM representing status: LOADING, SUCCESS, or ERROR
     * @param data object of type T representing the type of data associated with a process
     * @param message variable that stores the error message if needed
     */
    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     *
     * @param data object of type T, represents the RequestType of this Resource object
     * @return Resource<T>: a new Resource(LOADING, data, null) object with status loading
     */
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    /**
     *Returns a new Resource object with status SUCCESS and data.
     * @param data object of type T, representing the RequestType of this Resource object
     * @return Resource<T>: a new Resource(SUCCESS, data, null) object with status success
     */
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    /**
     *Returns a new Resource object with status ERROR, data, and error message msg.
     * @param msg the error message
     * @param data object of type T, representing the RequestType of this Resource object
     * @return Resource<T>: a new Resource(ERROR, data, msg) object with status error
     */
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }
}
