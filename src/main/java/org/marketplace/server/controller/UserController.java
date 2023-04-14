package org.marketplace.server.controller;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.UserAuthenticatorService;
import org.marketplace.server.service.UserRegistrationService;
import org.marketplace.server.common.exceptions.UserAuthenticationException;
import org.marketplace.server.common.exceptions.UserRegistrationException;

public class UserController {
    private final UserAuthenticatorService userAuthenticatorService;
    private final UserRegistrationService userRegistrationService;

    public UserController() {
        this.userAuthenticatorService = new UserAuthenticatorService();
        this.userRegistrationService = new UserRegistrationService();
    }

    public void handleUserAuthentication(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            User loggedInUser = userAuthenticatorService.authenticate(username, password);
            ctx.sessionAttribute("userId", loggedInUser.getId());
            ctx.status(HttpStatus.OK_200);
        } catch (UserAuthenticationException e) {
            System.out.println(e.getMessage());
            ctx.status(HttpStatus.UNAUTHORIZED_401).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void handleUserRegistration(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        String dateOfBirth = ctx.formParam("dateOfBirth");

        try {
            userRegistrationService.register(firstName, lastName, email, dateOfBirth, username, password);
            ctx.status(HttpStatus.CREATED_201);
        } catch (UserRegistrationException e) {
            System.out.println(e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST_400).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void handleUserLogout(Context context) {
        HttpSession session = context.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        context.redirect("/login");
    }
}
