package org.marketplace.server.service;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import org.marketplace.server.common.exceptions.UserNotFoundException;
import org.marketplace.server.model.User;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = UserRepository.getInstance();
    }

    public User findUserById(Integer userId) throws UserNotFoundException {
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
