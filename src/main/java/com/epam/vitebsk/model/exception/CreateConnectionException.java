package com.epam.vitebsk.model.exception;

public class CreateConnectionException extends RuntimeException {

    public CreateConnectionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public CreateConnectionException(String arg0) {
        super(arg0);
    }

    public CreateConnectionException(Throwable arg0) {
        super(arg0);
    }

}
