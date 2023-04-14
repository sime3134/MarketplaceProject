package org.marketplace.server.service;

import org.marketplace.server.model.User;
import org.marketplace.server.model.UserRepository;
import org.marketplace.server.service.exceptions.UserAuthenticationException;

public class UserAuthenticatorService {
    private final UserRepository userRepository;

    public UserAuthenticatorService() {
        userRepository = UserRepository.getInstance();
    }

    public User authenticate(String username, String password) throws UserAuthenticationException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UserAuthenticationException("The provided username is not registered.");
        }

        boolean passwordMatches = HashingService.verifyPassword(password, user.getHashedPassword());

        if (!passwordMatches) {
            throw new UserAuthenticationException("The provided password is incorrect.");
        }

        return user;
    }
}
