package org.marketplace.server.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.marketplace.server.common.exceptions.CartException;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.CartService;

public class CartController {

    private CartService cartService;

    public CartController() {
        cartService = new CartService();
    }

    public void addToCart(Context ctx) {
        Integer productId = ctx.pathParam("productId") != null ?
                Integer.valueOf(ctx.pathParam("productId")) : null;

        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            cartService.addProductToCart(productId, userId);
            ctx.status(HttpStatus.OK);
        } catch (CartException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }

    }

    public void removeFromCart(Context ctx) {

    }
}
