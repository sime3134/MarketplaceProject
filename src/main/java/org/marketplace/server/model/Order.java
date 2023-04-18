package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private static int nextId = 0;
    private int id;
    private LocalDate date;
    private double amount;
    private User buyer;
    private List<Product> products;

    private OrderStatus orderStatus;

    public Order(LocalDate date, User buyer, List<Product> products) {
        this.id = nextId++;
        this.date = date;
        this.buyer = buyer;
        this.products = products;
        this.amount = calculateAmount();
        this.orderStatus = OrderStatus.PENDING;
    }

    private double calculateAmount() {
        amount = 0;
        for(Product product : products) {
            amount += product.getProductPrice();
        }
        return amount;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    @JsonIgnoreProperties({"shoppingCart"})
    public User getBuyer() {
        return buyer;
    }
}
