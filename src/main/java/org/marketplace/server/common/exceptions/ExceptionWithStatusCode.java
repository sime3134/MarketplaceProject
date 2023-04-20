package org.marketplace.server.common.exceptions;

public abstract class ExceptionWithStatusCode extends Exception {

    private final int statusCode;

    protected ExceptionWithStatusCode(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatus(){
        return statusCode;
    }

}
