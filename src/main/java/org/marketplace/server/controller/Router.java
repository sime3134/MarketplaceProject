package org.marketplace.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Router {
    private final String apiPrefix = "/api/v1";

    private final OrderController orderController;
    private final ProductController productController;
    private final UserController userController;

    private final ObjectMapper objectMapper;

    public Router() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        orderController = new OrderController();
        productController = new ProductController(objectMapper);
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
