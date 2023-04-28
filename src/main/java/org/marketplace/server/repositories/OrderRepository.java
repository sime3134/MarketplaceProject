package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final Database database;
    private static OrderRepository instance;

    private OrderRepository() {
        this.database = Database.getInstance();
    }

    public static OrderRepository getInstance() {
        if(instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public List<Order> getAllOrders() {
        return database.getOrderTable();
    }

    public void addNewOrder(Order order) {
        database.addOrder(order);
    }

    public void removeOrder(Order order) {
        database.removeOrder(order);
    }
}
