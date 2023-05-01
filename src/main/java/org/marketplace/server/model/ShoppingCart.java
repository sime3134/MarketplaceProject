package org.marketplace.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create a shopping cart for users with functionalities such as:
 *      adding a product to cart, removing a product and retreiving a product from cart.
 */

public class ShoppingCart {

    private final List<Product> productsInCart;
    private double totalPrice;

    public ShoppingCart () {
        totalPrice = 0;
        productsInCart = new ArrayList<>();
    }

    public void addProductToCart(Product product) {
        productsInCart.add(product);
        totalPrice += product.getProductPrice();
    }

    public void removeProductFromCart(Product product) {
        productsInCart.remove(product);
        totalPrice -= product.getProductPrice();
    }

    public List<Product> getProducts() {
        return productsInCart;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
