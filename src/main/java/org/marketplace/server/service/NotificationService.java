package org.marketplace.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.NotificationException;
import org.marketplace.server.model.MessageType;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.dto.NotificationSocketMessage;
import org.marketplace.server.model.notifications.PurchaseNotification;
import org.marketplace.server.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {

    private final Map<Integer, WsContext> userWsMap;

    private final ObjectMapper objectMapper;

    private static NotificationService instance;

    private final UserRepository userRepository;

    public static NotificationService getInstance(ObjectMapper objectMapper) {
        if(instance == null) {
            instance = new NotificationService(objectMapper);
        }
        return instance;
    }

    private NotificationService(ObjectMapper objectMapper) {
        userWsMap = new ConcurrentHashMap<>();
        this.objectMapper = objectMapper;
        userRepository = UserRepository.getInstance();
    }

    public void addUserWs(WsConnectContext ctx, Integer userId) {
        userWsMap.put(userId, ctx);
    }

    public void removeUserWs(Integer userId) {
        userWsMap.remove(userId);
    }

    public WsContext getUserWs(Integer userId) {
        return userWsMap.get(userId);
    }

    public void notifySellers(List<Order> orders) throws JsonProcessingException, NotificationException {

        for(Order order : orders) {
            WsContext sellerCtx = getUserWs(order.getProduct().getSeller().getId());

            Notification notification = new PurchaseNotification(order.getBuyer().getUsername(),
                    order.getProduct().getProductType().getName(), order.getProduct().getProductPrice(), order.getId());

            userRepository.addNotification(order.getProduct().getSeller(), notification);

            if(sellerCtx != null) {
                NotificationSocketMessage notificationSocketMessage = new NotificationSocketMessage("New order placed for your " +
                        "product: " + order.getProduct().getProductType().getName(), MessageType.INFO);
                String json;
                json = objectMapper.writeValueAsString(notificationSocketMessage);
                try {
                    sellerCtx.send(json);
                } catch (Exception e) {
                    throw new NotificationException("Error while sending " +
                            "notification to seller.", HttpStatus.INTERNAL_SERVER_ERROR_500);
                }
            }
        }
    }
}
