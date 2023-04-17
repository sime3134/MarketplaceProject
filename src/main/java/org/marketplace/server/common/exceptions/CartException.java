package org.marketplace.server.common.exceptions;

public class CartException extends Exception {

    private final int statusCode;

    public CartException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatus() {
        return statusCode;
    }
}
