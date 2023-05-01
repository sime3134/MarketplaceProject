package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.Hasher;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;
import org.marketplace.server.common.exceptions.UserAuthenticationException;

/**
 * This class is responsible for authenticating users
 * by verifying that their login details are correct.
 */
public class UserAuthenticatorService {
    private final UserRepository userRepository;
    public UserAuthenticatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) throws UserAuthenticationException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UserAuthenticationException("The provided username or password is incorrect.", HttpStatus.UNAUTHORIZED_401);
        }

        boolean passwordMatches = Hasher.verifyPassword(password, user.getHashedPassword());

        if (!passwordMatches) {
            throw new UserAuthenticationException("The provided username or password is incorrect.", HttpStatus.UNAUTHORIZED_401);
        }

        return user;
    }
}
