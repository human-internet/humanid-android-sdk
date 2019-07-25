package com.humanid;

import android.support.annotation.NonNull;

public class HumanIDException extends RuntimeException {

    static final long serialVersionUID = 1;

    public HumanIDException() {
        super();
    }

    public HumanIDException(@NonNull String message) {
        super(message);
    }

    public HumanIDException(@NonNull String format, @NonNull Object... args) {
        this(String.format(format, args));
    }

    public HumanIDException(@NonNull String message, @NonNull Throwable throwable) {
        super(message, throwable);
    }

    public HumanIDException(@NonNull Throwable throwable) {
        super(throwable);
    }

    @NonNull
    @Override
    public String toString() {
        return getMessage();
    }
}
