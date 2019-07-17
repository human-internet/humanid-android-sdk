package com.humanid;

public class HumanIDException extends RuntimeException {

    static final long serialVersionUID = 1;

    public HumanIDException() {
        super();
    }

    public HumanIDException(String message) {
        super(message);
    }

    public HumanIDException(String format, Object... args) {
        this(String.format(format, args));
    }

    public HumanIDException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HumanIDException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
