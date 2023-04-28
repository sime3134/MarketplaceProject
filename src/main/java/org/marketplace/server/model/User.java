package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.marketplace.server.common.Observable;
import org.marketplace.server.common.Observer;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.model.notifications.PurchaseNotification;
import org.marketplace.server.model.notifications.SubscriptionNotification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private String username;
    private String hashedPassword;

    private int id;

    private static int nextId = 0;

    private List<Notification> notifications;

    private ShoppingCart shoppingCart;

    @JsonCreator
    public User(@JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("emailAddress") String emailAddress,
                @JsonProperty("dateOfBirth") JsonNode dateOfBirth,
                @JsonProperty("username") String username,
                @JsonProperty("hashedPassword") String hashedPassword,
                @JsonProperty("notifications") List<Notification> notifications,
                @JsonProperty("id") int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = LocalDate.of(dateOfBirth.get(0).asInt(), dateOfBirth.get(1).asInt(), dateOfBirth.get(2).asInt());
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.notifications = notifications != null ? notifications : new ArrayList<>();
        this.id = id;
        shoppingCart = new ShoppingCart();
    }

    public User(String firstName, String lastName, String emailAddress,
                LocalDate dateOfBirth, String username, String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.hashedPassword = hashedPassword;
        notifications = new ArrayList<>();
        shoppingCart = new ShoppingCart();
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

    @JsonIgnore
    public String getIdAsString() {
        return String.valueOf(id);
    }

    public int getId() {
        return id;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public void update(Observable observable) {
        notifications.add(new SubscriptionNotification());
        //TODO: web socket call to notify the user
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
}
