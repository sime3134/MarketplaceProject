package org.marketplace.server.service;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;
import org.marketplace.server.model.Role;

import java.util.Set;

public class AccessService implements AccessManager {
    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx,
                       @NotNull Set<? extends RouteRole> permittedRoles) throws Exception {
        Role userRole = getUserRole(ctx); // Implement this method to determine the user's role
        if (permittedRoles.contains(userRole)) {
            handler.handle(ctx);
        } else {
            ctx.redirect("/login");
        }
    }

    private Role getUserRole(Context ctx) {
        String userId = ctx.sessionAttribute("userId");
        if (userId == null) {
            return Role.ANYONE;
        } else {
            return Role.USER;
        }
    }
}
