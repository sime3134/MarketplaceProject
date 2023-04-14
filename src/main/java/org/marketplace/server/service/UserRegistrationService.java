package org.marketplace.server.service;

import org.marketplace.server.common.DateParser;
import org.marketplace.server.model.User;
import org.marketplace.server.model.UserRepository;
import org.marketplace.server.service.exceptions.UserRegistrationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserRegistrationService {
    private final UserRepository userRepository;

    public UserRegistrationService() {
        userRepository = UserRepository.getInstance();
    }

    public User register(String firstName, String lastName, String email, String dateOfBirth, String username,
                         String password) throws UserRegistrationException {
        UserValidator.validateUsername(username);
        UserValidator.validatePassword(password);
        UserValidator.validateFirstName(firstName);
        UserValidator.validateLastName(lastName);
        UserValidator.validateEmail(email);
        UserValidator.validateDateOfBirth(dateOfBirth);
        LocalDate parsedDateOfBirth;
        try{
            parsedDateOfBirth = DateParser.parse(dateOfBirth);
        } catch (DateTimeParseException e) {
            throw new UserRegistrationException("Not a valid date of birth");
        }

        if (userRepository.findUserByUsername(username) != null) {
            throw new UserRegistrationException("This username is already taken.");
        }

        String hashedPassword = HashingService.hashPassword(password);

        User newUser = new User(firstName, lastName, email, parsedDateOfBirth, username, hashedPassword);
        boolean success = userRepository.addUser(newUser);

        if (success) {
            return newUser;
        } else {
            throw new UserRegistrationException("Something went wrong while registering the user.");
        }
    }
}
