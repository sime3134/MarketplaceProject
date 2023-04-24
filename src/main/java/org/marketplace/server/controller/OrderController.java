package org.marketplace.server.controller;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.common.exceptions.OrderException;
import org.marketplace.server.common.exceptions.UserException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.OrderService;
import org.marketplace.server.service.UserService;

import java.util.List;

public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController() {
        orderService = new OrderService();
        userService = new UserService();
    }

    public void placeOrder(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            User user = userService.findUserById(userId);
            List<Order> orders = orderService.placeOrder(user);
            ctx.status(HttpStatus.OK_200).json(orders);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void getUserOrders(Context context) {
        Integer userId = context.sessionAttribute("userId") != null ?
                Integer.valueOf(context.sessionAttribute("userId")) : null;
        try {
            User user = userService.findUserById(userId);

            List<Order> orders = orderService.getUserOrders(user);
            context.header("Content-type", "application/json").json(orders);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            context.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
