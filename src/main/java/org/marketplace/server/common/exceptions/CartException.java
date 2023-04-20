package org.marketplace.server.common.exceptions;

public class CartException extends ExceptionWithStatusCode {
    public CartException(String message, int statusCode) {
        super(message, statusCode);
    }
}
