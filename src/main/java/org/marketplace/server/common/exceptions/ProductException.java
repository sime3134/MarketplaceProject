package org.marketplace.server.common.exceptions;

public class ProductException extends ExceptionWithStatusCode {

    public ProductException(String message, int statusCode) {
        super(message, statusCode);
    }
}
