package org.marketplace.server.service;

import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = UserRepository.getInstance();
    }

    public User findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
