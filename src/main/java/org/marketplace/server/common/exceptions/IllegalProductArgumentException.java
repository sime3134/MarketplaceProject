package org.marketplace.server.common.exceptions;

public class IllegalProductArgumentException extends ProductException {
    public IllegalProductArgumentException(String message, int statusCode) {
        super(message, statusCode);
    }
}
