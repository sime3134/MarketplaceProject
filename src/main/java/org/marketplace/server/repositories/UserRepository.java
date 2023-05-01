package org.marketplace.server.repositories;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.UserRegistrationException;
import org.marketplace.server.database.Database;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.model.User;

public class UserRepository {
    private static UserRepository instance;
    private final Database database;

    public UserRepository(Database database) {
        this.database = database;
    }

    public synchronized User findUserByUsername(String username) {
        return database.findUserByUsername(username);
    }

    public synchronized User findUserById(Integer id) {
        return database.findUserById(id);
    }

    public synchronized void addUser(User newUser) throws UserRegistrationException {
        if(findUserByUsername(newUser.getUsername()) != null) {
            throw new UserRegistrationException("User with username " + newUser.getUsername() + " already " +
                    "exists!", HttpStatus.CONFLICT_409);
        }
        database.addUser(newUser);
    }

    public synchronized void addNotification(User user, Notification notification) {
        database.addNotificationToUser(user, notification);
    }

    public synchronized void removeNotification(User user, Integer notificationIndex) {
        database.removeNotificationFromUser(user, notificationIndex);
    }

    public synchronized void addSubscription(User user, ProductType productType) {
        database.addSubscriptionToUser(user, productType);
    }

    public synchronized void removeSubscription(User user, ProductType productType) {
        database.removeSubscriptionFromUser(user, productType);
    }
}
