package org.marketplace.server;

import org.marketplace.server.controller.Router;

public class ServerLauncher {
    public static void main(String[] args) {
        new Router().startServer();
    }
}