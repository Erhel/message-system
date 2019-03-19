package com.epam.vitebsk.model.exception;

public class BuildSqlException extends RuntimeException {

    public BuildSqlException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public BuildSqlException(String arg0) {
        super(arg0);
    }

    public BuildSqlException(Throwable arg0) {
        super(arg0);
    }

}
