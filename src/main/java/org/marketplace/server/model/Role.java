package org.marketplace.server.model;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {
    USER,
    ANYONE
}
