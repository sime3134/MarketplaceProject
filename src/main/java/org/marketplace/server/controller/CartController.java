package org.marketplace.server.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ShoppingCart;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.CartService;
import org.marketplace.server.service.OrderService;
import org.marketplace.server.service.ProductService;
import org.marketplace.server.service.UserService;

/**
 * Class used to controll different functionality for the users shopping cart
 *      these functionalities include: adding products to a cart, removing them from the cart and to retreive the cart
 */

public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    private final OrderService orderService;

    public CartController() {
        cartService = new CartService();
        userService = new UserService();
        productService = new ProductService();
        orderService = new OrderService();
    }

    public void addToCart(Context ctx) {
        Integer productId = ctx.pathParam("productId") != null ?
                Integer.valueOf(ctx.pathParam("productId")) : null;

        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            User user = userService.findUserById(userId);
            Product product = productService.findProductById(productId);

            cartService.addProductToCart(product,
                    user, orderService.getUserOrders(user));
            ctx.status(HttpStatus.OK);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }

    }

    public void removeFromCart(Context ctx) {
        Integer productId = ctx.pathParam("productId") != null ?
                Integer.valueOf(ctx.pathParam("productId")) : null;

        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            User user = userService.findUserById(userId);
            Product product = productService.findProductById(productId);
            cartService.removeProductFromCart(product, user);
            ctx.status(HttpStatus.OK);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void getCart(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            User user = userService.findUserById(userId);
            ShoppingCart userCart = cartService.getCart(user);
            ctx.header("Content-type", "application/json").json(userCart);
        } catch(ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
