package com.criteo.exceptions;

public class FailedToCreateException extends RuntimeException {

    public FailedToCreateException() {

    }

    public FailedToCreateException(String message) {
        super(message);
    }

    public FailedToCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToCreateException(Throwable cause) {
        super(cause);
    }

}
