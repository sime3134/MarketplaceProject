package org.marketplace.server.controller;

import io.javalin.Javalin;
import org.marketplace.server.model.Database;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Router {
    private final String apiPrefix = "/api/v1";

    public void startServer() {
        Javalin app = Javalin.create(config -> {
            JavalinThymeleaf.init();
            config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
            config.staticFiles.add("/view/static");
        });
        //Starts the server
        app.start(5001);

        //TODO: Add API endpoints here and add authentication roles

        //Just for testing
        app.get("/", ctx -> {
            ctx.render("/view/templates/index.html");
        });
    }
}
