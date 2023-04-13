package org.marketplace.server.controller;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.UserAuthenticatorService;
import org.marketplace.server.service.UserRegistrationService;
import org.marketplace.server.service.exceptions.UserAuthenticationException;
import org.marketplace.server.service.exceptions.UserRegistrationException;

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
            userAuthenticatorService.authenticate(username, password);
            ctx.status(HttpStatus.OK_200);
            // Handle successful authentication, e.g. redirect to the home page
        } catch (UserAuthenticationException e) {
            ctx.status(HttpStatus.UNAUTHORIZED_401).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void handleUserRegistration(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            userRegistrationService.register(username, password);
            ctx.status(HttpStatus.CREATED_201);
            // Handle successful registration, e.g. redirect to the login page
        } catch (UserRegistrationException e) {
            ctx.status(HttpStatus.BAD_REQUEST_400).json(new ErrorResponse(e.getMessage()));
        }
    }
}
