package org.marketplace.server.controller;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.UserAuthenticatorService;
import org.marketplace.server.service.UserRegistrationService;
import org.marketplace.server.service.exceptions.UserAuthenticationException;
import org.marketplace.server.service.exceptions.UserRegistrationException;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            ctx.status(HttpStatus.UNAUTHORIZED_401).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void handleUserRegistration(Context ctx) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String email = ctx.formParam("email");
        LocalDate dateOfBirth = LocalDate.parse(ctx.formParam("dateOfBirth"), dateFormat);

        try {
            userRegistrationService.register(firstName, lastName, email, dateOfBirth, username, password);
            ctx.status(HttpStatus.CREATED_201);
        } catch (UserRegistrationException e) {
            ctx.status(HttpStatus.BAD_REQUEST_400).json(new ErrorResponse(e.getMessage()));
        }
    }
}
