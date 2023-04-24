package org.marketplace.server.common.exceptions;

public class ProductTypeException extends ExceptionWithStatusCode {

    protected ProductTypeException(String message, int statusCode) {
        super(message, statusCode);
    }
}
