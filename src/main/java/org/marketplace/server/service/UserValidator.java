package org.marketplace.server.service;

import org.marketplace.server.common.AppConstants;
import org.marketplace.server.service.exceptions.UserRegistrationException;

public class UserValidator {

    private UserValidator() {}

    public static void validateUsername(String username) throws UserRegistrationException {
        if (username == null || username.length() < AppConstants.MIN_USERNAME_LENGTH) {
            throw new UserRegistrationException("The provided username is too short.");
        }
    }

    public static void validatePassword(String password) throws UserRegistrationException {
        if (password == null || password.length() < AppConstants.MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("The provided password is too short.");
        }
    }
}
