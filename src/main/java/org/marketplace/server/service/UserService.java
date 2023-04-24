package org.marketplace.server.service;

import org.marketplace.server.common.exceptions.UserNotFoundException;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = UserRepository.getInstance();
    }

    public User findUserById(int userId) throws UserNotFoundException {
        User user = userRepository.findUserById(userId);

        if(user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    public User findUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if(user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }
}
