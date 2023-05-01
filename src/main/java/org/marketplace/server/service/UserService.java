package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.SubscriptionException;
import org.marketplace.server.common.exceptions.UserNotFoundException;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.UserRepository;

import java.util.List;

/**
 * This class is responsible for some user related
 * operations such as finding user either with their
 * id or with their username.
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void addSubscription(User user, ProductType productType) throws SubscriptionException {

        List<Integer> subs = user.getSubscriptions();
        if(subs.contains(productType.getId())) {
            throw new SubscriptionException("You are already subscribed to this product type.", HttpStatus.CONFLICT_409);
        }

        userRepository.addSubscription(user, productType);
    }

    public void removeSubscription(User user, ProductType productType) {
        userRepository.removeSubscription(user, productType);
    }
}
