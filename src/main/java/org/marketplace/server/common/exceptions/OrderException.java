package org.marketplace.server.common.exceptions;

public class OrderException extends ExceptionWithStatusCode {
    public OrderException(String message, int statusCode) {
        super(message, statusCode);
    }
}
