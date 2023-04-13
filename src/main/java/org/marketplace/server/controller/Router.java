package org.marketplace.server.controller;

import io.javalin.Javalin;
import org.marketplace.server.model.Database;
import io.javalin.plugin.bundled.CorsPluginConfig;

public class Router {

    private final Database database;
    private final String apiPrefix = "/api/v1";

    public Router() {
        this.database = Database.getInstance();
    }

    public void startServer() {
        try(Javalin app =
                    Javalin.create(config -> {
                        config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
                        config.staticFiles.add("/public/static");
                    }
        )) {
            //Starts the server
            app.start(5001);
        }

        //TODO: Add endpoints
    }
}
