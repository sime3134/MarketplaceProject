package org.marketplace.server.common.exceptions;

public class UserException extends ExceptionWithStatusCode {

    public UserException(String message, int statusCode) {
        super(message,statusCode);
    }
}
