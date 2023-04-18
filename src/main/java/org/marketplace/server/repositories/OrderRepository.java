package org.marketplace.server.repositories;

import org.marketplace.server.model.Order;

import java.util.List;

public class OrderRepository {
    private static OrderRepository instance;

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        if(instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public List<Order> getAllOrders() {
        return null;
    }
}
