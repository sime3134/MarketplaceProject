package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.OrderException;
import org.marketplace.server.common.exceptions.OrderNotFoundException;
import org.marketplace.server.common.exceptions.UserNotFoundException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.OrderStatus;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.OrderRepository;
import org.marketplace.server.service.filters.MaxOrderDateFilter;
import org.marketplace.server.service.filters.MinOrderDateFilter;
import org.marketplace.server.service.pipelines.Pipeline;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        orderRepository = OrderRepository.getInstance();
    }

    public List<Order> placeOrder(User user) throws OrderException {

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

    public List<Order> getUserOrders(User user) {
        return orderRepository.getUserOrders(user);
    }

    public List<Order> getUserOrders(User user, LocalDate startDate, LocalDate endDate) {
        Pipeline<Order> pipeline = new Pipeline<>();

        if(startDate != null) {
            pipeline.addFilter(new MinOrderDateFilter(startDate));
        }
        if(endDate != null) {
            pipeline.addFilter(new MaxOrderDateFilter(endDate));
        }

        return pipeline.execute(orderRepository.getUserOrders(user));
    }

    public void updateOrderStatus(Integer orderId, User user, Boolean newOrderStatus) throws OrderException {
        Order order = orderRepository.findOrderById(orderId);

        if(order == null) {
            throw new OrderException("Order not found", HttpStatus.NOT_FOUND_404);
        }

        if(order.getProduct().getSeller().getId() != user.getId()) {
            throw new OrderException("You are not the owner of this order", HttpStatus.FORBIDDEN_403);
        }

        if(order.getOrderStatus() == OrderStatus.ACCEPTED) {
            throw new OrderException("Order already accepted", HttpStatus.CONFLICT_409);
        }

        orderRepository.updateOrder(order, newOrderStatus);
    }

    public Order findOrderById(Integer orderId) throws OrderNotFoundException {
        Order order = orderRepository.findOrderById(orderId);

        if(order == null) {
            throw new OrderNotFoundException();
        }

        return order;
    }
}
