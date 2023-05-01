package org.marketplace.server.model.dto;

/**
 * Class used for handling Error responses by storing the error message
 */

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
