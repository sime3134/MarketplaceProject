package org.marketplace.server.model;

import io.javalin.security.RouteRole;

/**
 * Enum type class used to specify different roles such as: User and Anyone
 */

public enum Role implements RouteRole {
    USER,
    ANYONE
}
