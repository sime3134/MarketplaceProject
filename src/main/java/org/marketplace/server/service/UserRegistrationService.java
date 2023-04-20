package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.DateParser;
import org.marketplace.server.common.Hasher;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;
import org.marketplace.server.common.exceptions.UserRegistrationException;

import java.time.LocalDate;
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
            throw new UserRegistrationException("Not a valid date of birth", HttpStatus.BAD_REQUEST_400);
        }

        if (userRepository.findUserByUsername(username) != null) {
            throw new UserRegistrationException("This username is already taken.", HttpStatus.BAD_REQUEST_400);
        }

        String hashedPassword = Hasher.hashPassword(password);

        User newUser = new User(firstName, lastName, email, parsedDateOfBirth, username, hashedPassword);
        userRepository.addUser(newUser);

        return newUser;
    }
}
