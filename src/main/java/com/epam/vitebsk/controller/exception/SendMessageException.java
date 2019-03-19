package com.epam.vitebsk.controller.exception;

public class SendMessageException extends RuntimeException {

    public SendMessageException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SendMessageException(String arg0) {
        super(arg0);
    }

    public SendMessageException(Throwable arg0) {
        super(arg0);
    }

}
