package org.marketplace.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.NotificationService;
import org.marketplace.server.service.OrderService;
import org.marketplace.server.service.UserService;

import java.util.List;

public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    private final NotificationService notificationService;

    public OrderController(ObjectMapper objectMapper) {
        orderService = new OrderService();
        userService = new UserService();
        notificationService = NotificationService.getInstance(objectMapper);
    }

    public void placeOrder(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        User user;
        List<Order> orders;

        try {
            user = userService.findUserById(userId);
            orders = orderService.placeOrder(user);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
            return;
        }

        try {
            notificationService.notifySellers(orders);
            ctx.status(HttpStatus.OK_200).json(orders);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500).json(new ErrorResponse(e.getMessage()));
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
