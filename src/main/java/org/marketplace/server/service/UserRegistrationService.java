package org.marketplace.server.service;

import org.marketplace.server.model.User;
import org.marketplace.server.model.UserRepository;
import org.marketplace.server.service.exceptions.UserRegistrationException;

public class UserRegistrationService {
    private final UserRepository userRepository;

    public UserRegistrationService() {
        userRepository = UserRepository.getInstance();
    }

    public User register(String username, String password) throws UserRegistrationException {
        UserValidator.validateUsername(username);
        UserValidator.validatePassword(password);

        if (userRepository.findUserByUsername(username) != null) {
            throw new UserRegistrationException("This username is already taken.");
        }

        String hashedPassword = HashingService.hashPassword(password);

        User newUser = new User(username, hashedPassword);
        boolean success = userRepository.addUser(newUser);

        if (success) {
            return newUser;
        } else {
            throw new UserRegistrationException("Something went wrong while registering the user.");
        }
    }
}
