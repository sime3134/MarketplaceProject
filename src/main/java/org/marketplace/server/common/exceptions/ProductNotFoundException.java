package org.marketplace.server.common.exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException() {
        super("Could not found the requested product", HttpStatus.NOT_FOUND_404);
    }
}
