package com.epam.vitebsk.model.exception;

public class MapperException extends RuntimeException {

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

}
