package org.marketplace.server.common.exceptions;

public class IllegalDateException extends ExceptionWithStatusCode {
    public IllegalDateException(String message, int status) {
        super(message, status);
    }
}
