package com.humanid;

import androidx.annotation.NonNull;

/**
 * Exception class for HumanID.
 */
public class HumanIDException extends RuntimeException {
    /**
     * Set to 1L. Serial version UID for Serializable cass.
     */
    static final long serialVersionUID = 1;

    /**
     * Constructor. Calls the corresponding RuntimeException constructor.
     */
    public HumanIDException() {
        super();
    }

    /**
     * Constructor. Calls the corresponding RuntimeException constructor, passing in message as the parameter.
     * @param message String object passed into RuntimeException object
     */
    public HumanIDException(@NonNull String message) {
        super(message);
    }

    /**
     * Constructor. Calls the corresponding RuntimeException constructor using a result from String.format(format, args) as a parameter.
     * @param format the format of the string being passed into String.format
     * @param args Object containing args for String.format
     */
    public HumanIDException(@NonNull String format, @NonNull Object... args) {
        this(String.format(format, args));
    }

    /**
     * Constructor. Calls the corresponding RuntimeException constructor that takes a String and Throwable as parameters.
     * @param message String object passed into RuntimeException object
     * @param throwable Throwable object passed into RuntimeException object
     */
    public HumanIDException(@NonNull String message, @NonNull Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructor. Calls the corresponding RuntimeException constructor that takes a Throwable as a parameter.
     * @param throwable Throwable object passed into RuntimeException object
     */
    public HumanIDException(@NonNull Throwable throwable) {
        super(throwable);
    }

    /**
     *Calls getMessage(), located in RuntimeException. Returns the result
     * @return message from getMessage()
     */
    @NonNull
    @Override
    public String toString() {
        return getMessage();
    }
}
