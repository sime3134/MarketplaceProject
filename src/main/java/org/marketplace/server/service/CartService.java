package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.CartException;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ShoppingCart;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.repositories.UserRepository;

public class CartService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public CartService() {
        userRepository = UserRepository.getInstance();
        productRepository = ProductRepository.getInstance();
    }

    public void addProductToCart(Integer productId, Integer userId) throws CartException {
        User user = userRepository.findUserById(userId);
        Product product = productRepository.getProductById(productId);

        if(user == null) {
            throw new CartException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND_404);
        }
        if(product == null) {
            throw new CartException("Product with id " + productId + " does not exist", HttpStatus.NOT_FOUND_404);
        }
        if(user.getCart().getProducts().contains(product)) {
            throw new CartException("Product with id " + productId + " already exists in cart", HttpStatus.CONFLICT_409);
        }

        user.getCart().addProductToCart(product);
    }

    public void removeProductFromCart(Integer productId, Integer userId) throws CartException {
        User user = userRepository.findUserById(userId);
        Product product = productRepository.getProductById(productId);

        if(user == null) {
            throw new CartException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND_404);
        }
        if(product == null) {
            throw new CartException("Product with id " + productId + " does not exist", HttpStatus.NOT_FOUND_404);
        }
        if(!user.getCart().getProducts().contains(product)) {
            throw new CartException("Product with id " + productId + " is not in your cart",
                    HttpStatus.NOT_FOUND_404);
        }

        user.getCart().removeProductFromCart(product);

    }

    public ShoppingCart getCart(Integer userId) throws CartException {
        User user = userRepository.findUserById(userId);

        if(user == null) {
            throw new CartException("User with id " + userId + " does not exist", HttpStatus.NOT_FOUND_404);
        }

        return user.getCart();
    }
}
