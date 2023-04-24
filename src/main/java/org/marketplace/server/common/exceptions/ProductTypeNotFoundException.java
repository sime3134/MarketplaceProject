package org.marketplace.server.common.exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class ProductTypeNotFoundException extends ProductTypeException {
    public ProductTypeNotFoundException() {
        super("The requested product type could not be found", HttpStatus.NOT_FOUND_404);
    }
}
