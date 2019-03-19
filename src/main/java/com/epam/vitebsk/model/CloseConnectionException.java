package com.epam.vitebsk.model;

public class CloseConnectionException extends RuntimeException {

    public CloseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseConnectionException(String message) {
        super(message);
    }

    public CloseConnectionException(Throwable cause) {
        super(cause);
    }

}
