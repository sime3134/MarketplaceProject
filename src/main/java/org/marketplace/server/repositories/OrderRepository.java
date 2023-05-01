package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.User;

import java.util.List;

/**
 *  A singleton class used for handling requests to the database considering its stored Orders.
 *      Can be used for retreiving all orders, adding new orders, removing orders, updating orders and finding
 *      orders by ID.
 */

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

    public Order findOrderById(Integer orderId) {
        List<Order> orders = database.getOrderTable();

        for(Order order : orders) {
            if(order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public void updateOrder(Order order, boolean newOrderStatus) {
        database.updateOrder(order, newOrderStatus);
    }

    public List<Order> getUserOrders(User user) {
        return database.getUserOrders(user);
    }
}
