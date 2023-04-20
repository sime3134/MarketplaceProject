package org.marketplace.server.common.exceptions;

public class UserRegistrationException extends ExceptionWithStatusCode {
    public UserRegistrationException(String message, int statusCode) {
        super(message, statusCode);
    }
}
