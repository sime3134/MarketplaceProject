package org.marketplace.server.controller;

import io.javalin.Javalin;
import org.marketplace.server.model.Role;

public class Router {
    private final String apiPrefix = "/api/v1";

    private final OrderController orderController;
    private final ProductController productController;
    private final UserController userController;

    public Router() {
        orderController = new OrderController();
        productController = new ProductController();
        userController = new UserController();
    }

    public void setupEndpoints(Javalin app) {
        //API endpoints

        app.get(apiPrefix + "/products", productController::sendAllProducts, Role.USER);

        app.get(apiPrefix + "/product_types", productController::sendAllProductTypes, Role.USER);

        app.post(apiPrefix + "/login", userController::handleUserAuthentication, Role.ANYONE);

        app.post(apiPrefix + "/register", userController::handleUserRegistration, Role.ANYONE);

        app.post(apiPrefix + "/logout", userController::handleUserLogout, Role.USER);

        //RENDERING

        app.get("/", ctx -> {
            ctx.render("/view/templates/index.html");
        }, Role.USER);

        app.get("/login", ctx -> {
            ctx.render("/view/templates/login.html");
        }, Role.ANYONE);

        app.get("/register", ctx -> {
            ctx.render("/view/templates/register.html");
        }, Role.ANYONE);
    }
}
