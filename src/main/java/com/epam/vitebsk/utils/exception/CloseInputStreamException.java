package com.epam.vitebsk.utils.exception;

public class CloseInputStreamException extends RuntimeException {

    public CloseInputStreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseInputStreamException(String message) {
        super(message);
    }

    public CloseInputStreamException(Throwable cause) {
        super(cause);
    }

}
