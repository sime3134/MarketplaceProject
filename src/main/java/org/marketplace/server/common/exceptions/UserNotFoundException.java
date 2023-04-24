package org.marketplace.server.common.exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super("The requested user could not be found", HttpStatus.NOT_FOUND_404);
    }
}
