package org.marketplace.server.common.exceptions;

public class UserAuthenticationException extends ExceptionWithStatusCode {
    public UserAuthenticationException(String message, int statusCode) {
        super(message, statusCode);
    }
}
