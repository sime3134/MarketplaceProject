package org.marketplace.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.marketplace.server.database.Database;
import org.marketplace.server.routing.Router;
import org.marketplace.server.service.AccessService;

/**
 * The class that starts the system.
 */
public class ServerLauncher {
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Configure Javalin to use our custom ObjectMapper
        JavalinJackson jj = new JavalinJackson(objectMapper);

        Javalin app = Javalin.create(config -> {
            JavalinThymeleaf.init();
            config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
            config.staticFiles.add("/view/static");
            config.jsonMapper(jj);
            config.accessManager(new AccessService());
        });

        Database database = new Database();
        //Starts the server
        app.start(8080);
        new Router(database).setupEndpoints(app);
    }
}