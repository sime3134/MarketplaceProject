package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.CartException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ShoppingCart;
import org.marketplace.server.model.User;

import java.util.List;

public class CartService {

    public void addProductToCart(Product product, User user, List<Order> userOrders) throws CartException {

        if(user == null) {
            throw new CartException("Could not find the user on the server.", HttpStatus.NOT_FOUND_404);
        }
        if(product == null) {
            throw new CartException("Could not find the product on the server.", HttpStatus.NOT_FOUND_404);
        }
        if(user.getCart().getProducts().contains(product)) {
            throw new CartException("You have already added this product to your cart.",
                    HttpStatus.CONFLICT_409);
        }
        if(userOrders.stream().anyMatch(order -> order.getProduct().getId() == product.getId())) {
            throw new CartException("You have already bought this product.", HttpStatus.CONFLICT_409);
        }

        user.getCart().addProductToCart(product);
    }

    public void removeProductFromCart(Product product, User user) throws CartException {

        if(user == null) {
            throw new CartException("User with received id does not exist", HttpStatus.NOT_FOUND_404);
        }
        if(product == null) {
            throw new CartException("Product with received id does not exist", HttpStatus.NOT_FOUND_404);
        }
        if(!user.getCart().getProducts().contains(product)) {
            throw new CartException("Product with id " + product.getId() + " does not exist in the cart",
                    HttpStatus.NOT_FOUND_404);
        }

        user.getCart().removeProductFromCart(product);

    }

    public ShoppingCart getCart(User user) throws CartException {

        if(user == null) {
            throw new CartException("User with the received id does not exist", HttpStatus.NOT_FOUND_404);
        }

        return user.getCart();
    }
}
