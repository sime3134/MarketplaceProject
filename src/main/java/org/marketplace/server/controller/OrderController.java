package org.marketplace.server.controller;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.CartException;
import org.marketplace.server.common.exceptions.OrderException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.OrderService;

import java.util.List;

public class OrderController {

    private OrderService orderService;

    public OrderController() {
        orderService = new OrderService();
    }
    public void placeOrder(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            orderService.placeOrder(userId);
            ctx.status(HttpStatus.OK_200);
        } catch (OrderException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void getUserOrders(Context context) {
        Integer userId = context.sessionAttribute("userId") != null ?
                Integer.valueOf(context.sessionAttribute("userId")) : null;

        try {
            List<Order> orders = orderService.getUserOrders(userId);
            context.header("Content-type", "application/json").json(orders);
        } catch (OrderException e) {
            System.out.println(e.getMessage());
            context.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
