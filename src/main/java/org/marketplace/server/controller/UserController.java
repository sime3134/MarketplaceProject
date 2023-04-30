package org.marketplace.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.websocket.WsConfig;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.common.exceptions.UserNotFoundException;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.service.NotificationService;
import org.marketplace.server.service.UserAuthenticatorService;
import org.marketplace.server.service.UserRegistrationService;
import org.marketplace.server.common.exceptions.UserAuthenticationException;
import org.marketplace.server.common.exceptions.UserRegistrationException;
import org.marketplace.server.service.UserService;

import java.time.Duration;
import java.util.List;

public class UserController {
    private final UserAuthenticatorService userAuthenticatorService;
    private final UserRegistrationService userRegistrationService;

    private final UserService userService;

    private final NotificationService notificationService;

    public UserController() {
        this.userAuthenticatorService = new UserAuthenticatorService();
        this.userRegistrationService = new UserRegistrationService();
        this.notificationService = NotificationService.getInstance();
        this.userService = new UserService();
    }

    public void handleUserAuthentication(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            User loggedInUser = userAuthenticatorService.authenticate(username, password);
            ctx.sessionAttribute("userId", loggedInUser.getIdAsString());
            ctx.redirect("/");
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
            ctx.header("Cache-Control", "no-cache").header("Pragma", "no-cache").header("Expires", "0");
            ctx.status(HttpStatus.CREATED_201);
        } catch (UserRegistrationException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void handleUserLogout(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ctx.header("Cache-Control", "no-cache").header("Pragma", "no-cache").header("Expires", "0");
        ctx.redirect("/login");
    }

    public void handleUserConnection(WsConfig wsConfig) {
        wsConfig.onConnect(ctx -> {
            ctx.session.setIdleTimeout(Duration.ofDays(1));
            Integer userId = ctx.sessionAttribute("userId") != null ?
                    Integer.parseInt(ctx.sessionAttribute("userId")) : null;

            notificationService.addUserWs(ctx, userId);
        });
        wsConfig.onClose(ctx -> {
            Integer userId = ctx.sessionAttribute("userId") != null ?
                    Integer.parseInt(ctx.sessionAttribute("userId")) : null;

            notificationService.removeUserWs(userId);
        });
    }

    public void getUserNotifications(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            User user = userService.findUserById(userId);

            List<Notification> notifications = user.getNotifications();

            ctx.header("Content-type", "application/json").json(notifications);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void removeNotification(Context ctx) {
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        Integer notificationIndex = Integer.valueOf(ctx.pathParam("notificationIndex"));

        try {
            User user = userService.findUserById(userId);

            notificationService.removeNotification(user, notificationIndex);
            ctx.status(HttpStatus.OK_200);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
