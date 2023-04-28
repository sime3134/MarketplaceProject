package org.marketplace.server.common.exceptions;

public class NotificationException extends ExceptionWithStatusCode {
    public NotificationException(String message, int statusCode) {
        super(message, statusCode);
    }
}
