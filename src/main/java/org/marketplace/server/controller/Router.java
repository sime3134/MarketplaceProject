package org.marketplace.server.controller;

import io.javalin.Javalin;
import org.marketplace.server.model.Database;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Router {

    private final Database database;
    private final String apiPrefix = "/api/v1";

    public Router() {
        this.database = Database.getInstance();
    }

    public void startServer() {
        Javalin app = Javalin.create(config -> {
            JavalinThymeleaf.init();
            config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
            config.staticFiles.add("/view/static");
        });
        //Starts the server
        app.start(5001);

        //Just for testing
        app.get("/", ctx -> {
            ctx.render("/view/templates/index.html");
        });
    }
}
