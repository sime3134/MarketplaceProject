package org.marketplace.server.common.exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException() {
        super("The requested order could not be found", HttpStatus.NOT_FOUND_404);
    }
}
