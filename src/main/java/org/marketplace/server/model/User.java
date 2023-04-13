package org.marketplace.server.model;

import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private String username;
    private String passwordHash;

    private List<String> notifications;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        notifications = new ArrayList<>();
    }

    @Override
    public void update(Observable observable) {
        notifications.add("A product type that you have subscribed to has been added to the marketplace.");
        //TODO: web socket call to notify the user
    }
}
