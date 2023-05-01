package org.marketplace.server.controller;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.common.exceptions.OrderException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.*;

import java.time.LocalDate;
import java.util.List;

/**
 * class used to handle different operations for an order placed by a user.
 *      operations include: placing order, get users orders and update statuses of products
 */

public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    private final NotificationService notificationService;

    private final ProductService productService;

    private final FormValidator formValidator;

    public OrderController(ServiceHandler serviceHandler) {
        orderService = serviceHandler.getOrderService();
        userService = serviceHandler.getUserService();
        notificationService = serviceHandler.getNotificationService();
        productService = serviceHandler.getProductService();
        formValidator = new FormValidator();
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
        }


    }

    public void getUserOrders(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        String startDateString = ctx.queryParam("startDate") != null && !ctx.queryParam("startDate").isBlank() ?
                ctx.queryParam("startDate") : null;
        String endDateString = ctx.queryParam("endDate") != null && !ctx.queryParam("endDate").isBlank() ?
                ctx.queryParam("endDate") : null;

        try {
            LocalDate startDate = parseDate(startDateString);
            LocalDate endDate = parseDate(endDateString);

            formValidator.validateDateOrder(startDate, endDate);

            User user = userService.findUserById(userId);
            List<Order> orders = orderService.getUserOrders(user, startDate, endDate);

            ctx.header("Content-type", "application/json").json(orders);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    private LocalDate parseDate(String dateString) throws OrderException {
        if (dateString == null) {
            return null;
        }

        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new OrderException("Invalid date format in search.", HttpStatus.BAD_REQUEST_400);
        }
    }

    public void updateOrderAndProductStatus(Context ctx) {
        Integer orderId = ctx.pathParam("orderId") != null ?
                Integer.valueOf(ctx.pathParam("orderId")) : null;

        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        Boolean accepted = ctx.queryParam("accepted") != null ?
                Boolean.valueOf(ctx.queryParam("accepted")) : null;

        Integer notificationIndex = ctx.queryParam("notificationIndex") != null ?
                Integer.valueOf(ctx.queryParam("notificationIndex")) : null;

        Order order;

        try {
            User user = userService.findUserById(userId);
            order = orderService.findOrderById(orderId);
            orderService.updateOrderStatus(order, user, accepted);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
            return;
        }


        if(Boolean.TRUE.equals(accepted)) {
            try {
                productService.setProductAsSold(order.getProduct().getId());
            } catch (ExceptionWithStatusCode e) {
                System.out.println(e.getMessage());
                ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
                return;
            }
        }

        try {
            notificationService.notifyBuyer(order, accepted);
            User seller = userService.findUserById(order.getProduct().getSeller().getId());
            notificationService.removeNotification(seller, notificationIndex);
            ctx.status(HttpStatus.OK_200);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
