package com.epam.vitebsk.utils.exception;

public class LoadResourceException extends RuntimeException {

    public LoadResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadResourceException(String message) {
        super(message);
    }

    public LoadResourceException(Throwable cause) {
        super(cause);
    }

}
