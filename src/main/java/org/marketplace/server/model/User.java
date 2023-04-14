package org.marketplace.server.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final LocalDate dateOfBirth;
    private final String username;
    private final String hashedPassword;

    private final int id;

    private static int nextId = 0;

    private final List<String> notifications;

    public User(String firstName, String lastName, String emailAddress,
                LocalDate dateOfBirth, String username, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.hashedPassword = hashedPassword;
        notifications = new ArrayList<>();
        id = nextId++;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getId() {
        return id;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    @Override
    public void update(Observable observable) {
        notifications.add("A product type that you have subscribed to has been added to the marketplace.");
        //TODO: web socket call to notify the user
    }
}
