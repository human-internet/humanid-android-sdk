package com.humanid;

public class HumanIDSDKNotInitializeException extends HumanIDException {

    static final long serialVersionUID = 1;

    public HumanIDSDKNotInitializeException() {
        super();
    }

    public HumanIDSDKNotInitializeException(String message) {
        super(message);
    }

    public HumanIDSDKNotInitializeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HumanIDSDKNotInitializeException(Throwable throwable) {
        super(throwable);
    }
}
