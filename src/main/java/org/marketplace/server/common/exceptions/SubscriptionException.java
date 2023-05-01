package org.marketplace.server.common.exceptions;

public class SubscriptionException extends ExceptionWithStatusCode {
    public SubscriptionException(String message, int statusCode) {
        super(message, statusCode);
    }
}
