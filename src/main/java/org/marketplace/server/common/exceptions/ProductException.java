package org.marketplace.server.common.exceptions;

public class ProductException extends Exception {
    private final int statusCode;
    public ProductException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatus() {
        return statusCode;
    }
}
