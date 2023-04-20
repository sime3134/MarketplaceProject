package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.OrderException;
import org.marketplace.server.common.exceptions.UserException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.OrderRepository;
import org.marketplace.server.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        orderRepository = OrderRepository.getInstance();
    }

    public List<Order> placeOrder(User user) throws OrderException {

        if(user == null) {
            throw new OrderException("User with this id does not exist", HttpStatus.NOT_FOUND_404);
        }

        if(user.getCart().getProducts().isEmpty()) {
            throw new OrderException("Cart is empty", HttpStatus.CONFLICT_409);
        }

        List<Order> orders = new ArrayList<>();

        for(Product product : user.getCart().getProducts()) {
            Order order = new Order(LocalDateTime.now(), user, product);
            orderRepository.addNewOrder(order);
            orders.add(order);
        }
        user.getCart().getProducts().clear();

        return orders;
    }

    public List<Order> getUserOrders(User user) throws UserException {

        if(user == null) {
            throw new UserException("The requested user could not be found", HttpStatus.NOT_FOUND_404);
        }

        List<Order> allOrders = orderRepository.getAllOrders();

        return allOrders.stream().filter(order -> order.getBuyer().getId() == user.getId()).toList();
    }
}
