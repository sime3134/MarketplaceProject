package org.marketplace.server.service;

import org.marketplace.server.common.Hasher;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;
import org.marketplace.server.common.exceptions.UserAuthenticationException;

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

        boolean passwordMatches = Hasher.verifyPassword(password, user.getHashedPassword());

        if (!passwordMatches) {
            throw new UserAuthenticationException("The provided password is incorrect.");
        }

        return user;
    }
}
