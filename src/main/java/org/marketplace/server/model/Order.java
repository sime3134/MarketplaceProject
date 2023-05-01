package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

/**
 * class used to create an Order
 */

public class Order {

    private static int nextId = 0;
    private int id;
    private LocalDateTime timestamp;
    private User buyer;
    private Product product;
    private OrderStatus orderStatus;

    public Order () {

    }

    public Order(LocalDateTime timestamp, User buyer, Product product) {
        this.id = nextId++;
        this.timestamp = timestamp;
        this.buyer = buyer;
        this.product = product;
        this.orderStatus = OrderStatus.PENDING;
    }

    public Order(int id, User buyer, Product product, LocalDateTime date, OrderStatus orderStatus) {
        this.id = id;
        this.timestamp = date;
        this.buyer = buyer;
        this.product = product;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static void updateNextId(int maxId) {
        if (maxId >= nextId) {
            nextId = maxId + 1;
        }
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @JsonIgnoreProperties({"color", "yearOfProduction",
            "productCondition", "available"})
    public Product getProduct() {
        return product;
    }

    @JsonIgnoreProperties({"notifications","firstName","lastName","password", "shoppingCart", "dateOfBirth",
            "emailAddress", "username", "hashedPassword"})
    public User getBuyer() {
        return buyer;
    }

    public void setOrderStatus(Boolean accepted) {
        if (accepted) {
            this.orderStatus = OrderStatus.ACCEPTED;
        } else {
            this.orderStatus = OrderStatus.REJECTED;
        }
    }
}
