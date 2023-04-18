package org.marketplace.server.routing;

import io.javalin.Javalin;
import org.marketplace.server.controller.CartController;
import org.marketplace.server.controller.OrderController;
import org.marketplace.server.controller.ProductController;
import org.marketplace.server.controller.UserController;
import org.marketplace.server.model.Role;

public class Router {
    private final String apiPrefix = "/api/v1";

    private final OrderController orderController;
    private final ProductController productController;
    private final UserController userController;

    private final CartController cartController;

    public Router() {
        orderController = new OrderController();
        productController = new ProductController();
        userController = new UserController();
        cartController = new CartController();
    }

    public void setupEndpoints(Javalin app) {
        //API endpoints

        app.get(apiPrefix + "/product", productController::getFilteredProducts, Role.USER);

        app.get(apiPrefix + "/product_type", productController::getAllProductTypes, Role.USER);

        app.post(apiPrefix + "/login", userController::handleUserAuthentication, Role.ANYONE);

        app.post(apiPrefix + "/register", userController::handleUserRegistration, Role.ANYONE);

        app.post(apiPrefix + "/logout", userController::handleUserLogout, Role.USER);

        app.get(apiPrefix + "/cart", cartController::getCart, Role.USER);

        app.post(apiPrefix + "/cart/{productId}", cartController::addToCart, Role.USER);

        app.delete(apiPrefix + "/cart/{productId}", cartController::removeFromCart, Role.USER);

        app.get(apiPrefix + "/order", orderController::getUserOrders, Role.USER);

        app.post(apiPrefix + "/order", orderController::placeOrder, Role.USER);

        //RENDERING

        app.get("/", ctx -> ctx.render("/view/templates/index.html"), Role.USER);

        app.get("/login", ctx -> ctx.render("/view/templates/login.html"), Role.ANYONE);

        app.get("/register", ctx -> ctx.render("/view/templates/register.html"), Role.ANYONE);
    }
}
