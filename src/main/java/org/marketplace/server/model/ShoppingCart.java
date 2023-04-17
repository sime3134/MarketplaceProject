package org.marketplace.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingCart {
    private List<Product> productsInCart;
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
}
