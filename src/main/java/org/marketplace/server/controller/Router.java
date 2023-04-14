package org.marketplace.server.controller;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.template.JavalinThymeleaf;

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

    public void startServer() {
        Javalin app = Javalin.create(config -> {
            JavalinThymeleaf.init();
            config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
            config.staticFiles.add("/view/static");
        });
        //Starts the server
        app.start(5001);

        //API endpoints

        app.get(apiPrefix + "/products", productController::sendAllProducts);

        //RENDERING

        //Just for testing
        app.get("/", ctx -> {
            ctx.render("/view/templates/index.html");
        });
    }
}
