package org.marketplace.server.service;

import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.Observable;
import org.marketplace.server.common.Observer;
import org.marketplace.server.common.exceptions.NotificationException;
import org.marketplace.server.model.MessageType;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.dto.NotificationSocketMessage;
import org.marketplace.server.model.notifications.OrderStatusNotification;
import org.marketplace.server.model.notifications.PurchaseNotification;
import org.marketplace.server.model.notifications.SubscriptionNotification;
import org.marketplace.server.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that handles the notifications, for example
 * it notifies buyers and sellers.
 */
public class NotificationService implements Observer {

    private final Map<Integer, WsContext> userWsMap;

    private final UserRepository userRepository;

    public NotificationService(UserRepository userRepository) {
        userWsMap = new ConcurrentHashMap<>();
        this.userRepository = userRepository;
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

    public void notifySellers(List<Order> orders) throws NotificationException {

        for(Order order : orders) {
            WsContext sellerCtx = getUserWs(order.getProduct().getSeller().getId());

            Notification notification = new PurchaseNotification(order.getBuyer().getUsername(),
                    order.getProduct().getProductType().getName(), order.getProduct().getProductPrice(), order.getId());

            userRepository.addNotification(order.getProduct().getSeller(), notification);

            if(sellerCtx != null) {
                NotificationSocketMessage notificationSocketMessage = new NotificationSocketMessage("New order placed for your " +
                        "product: " + order.getProduct().getProductType().getName(), MessageType.PURCHASE_NOTIFICATION);

                try {
                    sellerCtx.send(notificationSocketMessage);
                } catch (Exception e) {
                    throw new NotificationException("Error while sending " +
                            "notification to seller.", HttpStatus.INTERNAL_SERVER_ERROR_500);
                }
            }
        }
    }

    public void notifyBuyer(Order order, Boolean accepted) throws NotificationException {
        WsContext buyerCtx = getUserWs(order.getBuyer().getId());

        Notification notification = new OrderStatusNotification("Your order for " + order.getProduct().getProductType().getName() +
                " has been " + (accepted ? "accepted" : "rejected") + " by " + order.getProduct().getSeller().getUsername() + ".");

        userRepository.addNotification(order.getBuyer(), notification);

        if(buyerCtx != null) {
            NotificationSocketMessage notificationSocketMessage = new NotificationSocketMessage("Your order for " +
                    order.getProduct().getProductType().getName() + " has been " + (accepted ? "accepted" : "rejected") +
                    " by " + order.getProduct().getSeller().getUsername() + ".",
                    MessageType.ORDER_STATUS_NOTIFICATION);

            try {
                buyerCtx.send(notificationSocketMessage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new NotificationException("Error while sending " +
                        "notification to seller.", HttpStatus.INTERNAL_SERVER_ERROR_500);
            }
        }
    }

    public void removeNotification(User user, Integer notificationIndex) throws NotificationException {

        if(notificationIndex == null) {
            throw new NotificationException("The notification could not be found on the server.",
                    HttpStatus.BAD_REQUEST_400);
        }
        userRepository.removeNotification(user, notificationIndex);
    }

    /**
     * Updates the observer by checking if the observable
     * object is an instance of ProductType. If it is we
     * get the subscribers of that product type and try to
     * send them a notification.
     */
    @Override
    public void update(Observable observable) {
        if(observable instanceof ProductType productType) {
            for(Integer userId : productType.getSubscribers()) {
                WsContext userCtx = getUserWs(userId);

                Notification notification = new SubscriptionNotification(productType.getName());

                addPersistentNotification(userId, notification);

                if(userCtx != null) {
                    NotificationSocketMessage notificationSocketMessage = new NotificationSocketMessage("A new " +
                            "product '" + productType.getName() + "' that you are subscribed to has been added " +
                            "to the market.",
                            MessageType.SUBSCRIPTION_NOTIFICATION);

                    try {
                        userCtx.send(notificationSocketMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void addPersistentNotification(Integer userId, Notification notification) {
        User user = null;

        try {
            user = userRepository.findUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user != null) {
            userRepository.addNotification(user, notification);
        }
    }
}
