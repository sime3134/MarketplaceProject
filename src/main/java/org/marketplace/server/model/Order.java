package org.marketplace.server.model;

import java.time.LocalDate;

public class Order {
    private String id;
    private String name;
    private LocalDate date;
    private String price;
    private String seller;
    private String buyer;

    public Order(String id, String name, LocalDate date, String price, String seller, String buyer) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.seller = seller;
        this.buyer = buyer;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        return buyer;
    }
}
