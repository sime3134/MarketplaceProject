package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.Hasher;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;
import org.marketplace.server.common.exceptions.UserRegistrationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * This class is responsible for handling
 * registration of new users
 */
public class UserRegistrationService {
    private final UserRepository userRepository;

    private final FormValidator formValidator;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        formValidator = new FormValidator();
    }

    public void register(String firstName, String lastName, String email, String dateOfBirth, String username,
                         String password) throws UserRegistrationException {

        formValidator.validateUsername(username);
        formValidator.validatePassword(password);
        formValidator.validateFirstName(firstName);
        formValidator.validateLastName(lastName);
        formValidator.validateEmail(email);
        formValidator.validateDateOfBirth(dateOfBirth);

        LocalDate parsedDateOfBirth = parseDateOfBirth(dateOfBirth);

        String hashedPassword = Hasher.hashPassword(password);

        User newUser = new User(firstName, lastName, email, parsedDateOfBirth, username, hashedPassword);
            userRepository.addUser(newUser);
    }

    private LocalDate parseDateOfBirth(String dateOfBirth) throws UserRegistrationException {
        try{
            return LocalDate.parse(dateOfBirth);
        } catch (DateTimeParseException e) {
            throw new UserRegistrationException("Not a valid date of birth", HttpStatus.BAD_REQUEST_400);
        }
    }
}
