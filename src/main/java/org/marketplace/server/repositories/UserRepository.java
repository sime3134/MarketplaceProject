package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.model.User;

public class UserRepository {
    private static UserRepository instance;
    private Database database;
    private UserRepository() {
        database = Database.getInstance();
    }
    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public synchronized User findUserByUsername(String username) {
        return database.findUserByUsername(username);
    }

    public synchronized User findUserById(Integer id) {
        return database.findUserById(id);
    }

    public synchronized void addUser(User newUser) {
        database.addUser(newUser);
    }

    public void addNotification(User user, Notification notification) {
        database.addNotificationToUser(user, notification);
    }

    public void removeNotification(User user, Integer notificationIndex) {
        database.removeNotificationFromUser(user, notificationIndex);
    }
}
