package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.AppConstants;
import org.marketplace.server.common.exceptions.UserRegistrationException;

public class UserValidator {

    private UserValidator() {}

    public static void validateUsername(String username) throws UserRegistrationException {
        if (username == null || username.length() < AppConstants.MIN_USERNAME_LENGTH) {
            throw new UserRegistrationException("The provided username is too short.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public static void validatePassword(String password) throws UserRegistrationException {
        if (password == null || password.length() < AppConstants.MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("The provided password is too short.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public static void validateFirstName(String firstName) throws UserRegistrationException {
        if(firstName == null || firstName.length() < AppConstants.MIN_FIRST_NAME_LENGTH) {
            throw new UserRegistrationException("First name not provided.", HttpStatus.BAD_REQUEST_400);
        }
    }
    public static void validateLastName(String lastName) throws UserRegistrationException {
        if(lastName == null || lastName.length() < AppConstants.MIN_LAST_NAME_LENGTH) {
            throw new UserRegistrationException("Last name not provided.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public static void validateEmail(String email) throws UserRegistrationException {
        if(email == null || email.length() < AppConstants.MIN_EMAIL_LENGTH) {
            throw new UserRegistrationException("The provided email is not valid", HttpStatus.BAD_REQUEST_400);
        }
    }

    public static void validateDateOfBirth(String dateOfBirth) throws UserRegistrationException {
        if (dateOfBirth == null || dateOfBirth.length() < AppConstants.MIN_DATE_OF_BIRTH_LENGTH) {
            throw new UserRegistrationException("Not a valid date of birth", HttpStatus.BAD_REQUEST_400);
        }

    }
}
