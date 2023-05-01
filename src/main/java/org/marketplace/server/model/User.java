package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.marketplace.server.model.notifications.Notification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private String username;
    private String hashedPassword;

    private int id;

    private static int nextId = 0;

    private List<Notification> notifications;

    private List<Integer> subscriptions;

    private ShoppingCart shoppingCart;

    @JsonCreator
    public User(@JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("emailAddress") String emailAddress,
                @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                @JsonProperty("username") String username,
                @JsonProperty("hashedPassword") String hashedPassword,
                @JsonProperty("notifications") List<Notification> notifications,
                @JsonProperty("subscriptions") List<Integer> subscriptions,
                @JsonProperty("id") int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = LocalDate.of(dateOfBirth.getYear(), dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.notifications = notifications;
        this.id = id;
        this.shoppingCart = new ShoppingCart();
        this.subscriptions = subscriptions;
    }

    public User(String firstName, String lastName, String emailAddress,
                LocalDate dateOfBirth, String username, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.notifications = new ArrayList<>();
        this.shoppingCart = new ShoppingCart();
        this.subscriptions = new ArrayList<>();
        this.id = nextId++;
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

    @JsonIgnore
    public String getIdAsString() {
        return String.valueOf(id);
    }

    public int getId() {
        return id;
    }

    public List<Integer> getSubscriptions() {
        return subscriptions;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
    @JsonIgnore
    public ShoppingCart getCart() {
        return shoppingCart;
    }

    public static void updateNextId(int maxId) {
        if (maxId >= nextId) {
            nextId = maxId + 1;
        }
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Integer notificationIndex) {
        notifications.remove(notificationIndex.intValue());
    }

    public void addSubscription(Integer id) {
        subscriptions.add(id);
    }

    public void removeSubscription(Integer id) {
        subscriptions.remove(id);
    }
}
