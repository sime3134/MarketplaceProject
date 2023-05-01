package org.marketplace.server.service;

import org.marketplace.server.database.Database;
import org.marketplace.server.repositories.OrderRepository;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.repositories.ProductTypeRepository;
import org.marketplace.server.repositories.UserRepository;

public class ServiceHandler {
    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final UserAuthenticatorService userAuthenticatorService;
    private final NotificationService notificationService;
    private final UserRegistrationService userRegistrationService;
    private final ProductTypeService productTypeService;

    public ServiceHandler(Database database){
        UserRepository userRepository = new UserRepository(database);
        OrderRepository orderRepository = new OrderRepository(database);
        ProductRepository productRepository = new ProductRepository(database);
        ProductTypeRepository productTypeRepository = new ProductTypeRepository(database);

        this.cartService = new CartService();
        this.orderService = new OrderService(orderRepository);
        this.productService = new ProductService(productRepository);
        this.userService = new UserService(userRepository);
        this.userAuthenticatorService = new UserAuthenticatorService(userRepository);
        this.notificationService = new NotificationService(userRepository);
        this.userRegistrationService = new UserRegistrationService(userRepository);
        this.productTypeService = new ProductTypeService(productTypeRepository);

        database.loadDatabase(notificationService);
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserAuthenticatorService getUserAuthenticatorService() {
        return userAuthenticatorService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public UserRegistrationService getUserRegistrationService() {
        return userRegistrationService;
    }

    public ProductTypeService getProductTypeService() {
        return productTypeService;
    }
}
