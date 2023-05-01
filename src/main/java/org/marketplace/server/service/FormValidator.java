package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.AppConstants;
import org.marketplace.server.common.exceptions.IllegalDateException;
import org.marketplace.server.common.exceptions.IllegalProductArgumentException;
import org.marketplace.server.common.exceptions.UserRegistrationException;

import java.time.LocalDate;

/**
 * Class that contains logic for validating all input
 * the user will give, for example checking that the
 * username contains enough characters
 */
public class FormValidator {

    public void validateUsername(String username) throws UserRegistrationException {
        if (username == null || username.length() < AppConstants.MIN_USERNAME_LENGTH) {
            throw new UserRegistrationException("The provided username is too short.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validatePassword(String password) throws UserRegistrationException {
        if (password == null || password.length() < AppConstants.MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("The provided password is too short.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateFirstName(String firstName) throws UserRegistrationException {
        if(firstName == null || firstName.length() < AppConstants.MIN_FIRST_NAME_LENGTH) {
            throw new UserRegistrationException("First name not provided.", HttpStatus.BAD_REQUEST_400);
        }
    }
    public void validateLastName(String lastName) throws UserRegistrationException {
        if(lastName == null || lastName.length() < AppConstants.MIN_LAST_NAME_LENGTH) {
            throw new UserRegistrationException("Last name not provided.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateEmail(String email) throws UserRegistrationException {
        if(email == null || email.length() < AppConstants.MIN_EMAIL_LENGTH) {
            throw new UserRegistrationException("The provided email is not valid", HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateDateOfBirth(String dateOfBirth) throws UserRegistrationException {
        if (dateOfBirth == null || dateOfBirth.length() < AppConstants.MIN_DATE_OF_BIRTH_LENGTH) {
            throw new UserRegistrationException("Not a valid date of birth", HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validatePrice(double price) throws IllegalProductArgumentException {
        if(price < 0.0) {
            throw new IllegalProductArgumentException("Please enter a price greater than 0.",
                    HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateColor(String color) throws IllegalProductArgumentException {
        if(color == null || color.isEmpty()) {
            throw new IllegalProductArgumentException("Please provide a product color.",
                    HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateYearOfProduction(String yearOfProduction) throws IllegalProductArgumentException {
        if(yearOfProduction == null || yearOfProduction.length() != 4) {
            throw new IllegalProductArgumentException("Please provide a valid year of production, 4 characters " +
                    "long.",
                    HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateDateOrder(LocalDate from, LocalDate to) throws IllegalDateException {
        if(from != null && to != null && (from.isAfter(to))) {
            throw new IllegalDateException("The start date cannot be after the end date.", HttpStatus.BAD_REQUEST_400);
        }
    }
}
