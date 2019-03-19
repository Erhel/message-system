package com.epam.vitebsk.controller.exception;

public class ControllerInstantiationException extends Exception {

    public ControllerInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerInstantiationException(String message) {
        super(message);
    }

    public ControllerInstantiationException(Throwable cause) {
        super(cause);
    }
}
