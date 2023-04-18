package org.marketplace.server.common.exceptions;

public class OrderException extends Exception {
    private int status;
    public OrderException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
