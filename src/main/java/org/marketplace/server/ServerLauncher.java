package org.marketplace.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.plugin.bundled.CorsPluginConfig;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.marketplace.server.controller.Router;
import org.marketplace.server.service.AccessService;

public class ServerLauncher {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        // Configure Javalin to use the custom ObjectMapper
        JavalinJackson jj = new JavalinJackson(objectMapper);

        Javalin app = Javalin.create(config -> {
            JavalinThymeleaf.init();
            config.plugins.enableCors(corsContainer -> corsContainer.add(CorsPluginConfig::anyHost));
            config.staticFiles.add("/view/static");
            config.jsonMapper(jj);
            config.accessManager(new AccessService());
        });
        //Starts the server
        app.start(5001);
        new Router().setupEndpoints(app);
    }
}