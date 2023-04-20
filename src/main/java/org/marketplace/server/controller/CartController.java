package org.marketplace.server.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.marketplace.server.common.exceptions.CartException;
import org.marketplace.server.common.exceptions.UserException;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ShoppingCart;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.service.CartService;
import org.marketplace.server.service.OrderService;
import org.marketplace.server.service.ProductService;
import org.marketplace.server.service.UserService;

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

        User user = userService.findUserById(userId);

        try {
            cartService.addProductToCart(productService.findProductById(productId),
                    user, orderService.getUserOrders(user));
            ctx.status(HttpStatus.OK);
        } catch (CartException | UserException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }

    }

    public void removeFromCart(Context ctx) {
        Integer productId = ctx.pathParam("productId") != null ?
                Integer.valueOf(ctx.pathParam("productId")) : null;
        Product product = productService.findProductById(productId);

        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;
        User user = userService.findUserById(userId);

        try {
            cartService.removeProductFromCart(product, user);
            ctx.status(HttpStatus.OK);
        } catch (CartException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void getCart(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;
        User user = userService.findUserById(userId);

        try {
            ShoppingCart userCart = cartService.getCart(user);
            ctx.header("Content-type", "application/json").json(userCart);
        } catch(CartException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
