package org.marketplace.server.model.notifications;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * abstract class for crating a notification message by specifying the message and its type.
 *      types include: purchase, Subscription and order status.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PurchaseNotification.class, name = "purchaseNotification"),
        @JsonSubTypes.Type(value = SubscriptionNotification.class, name = "subscriptionNotification"),
        @JsonSubTypes.Type(value = OrderStatusNotification.class, name = "orderStatusNotification")
})
public abstract class Notification {
    private final String message;
    private final String notificationType;
    protected Notification(String message, String notificationType) {
        this.message = message;
        this.notificationType = notificationType;
    }

    public String getMessage() {
        return message;
    }

    public String getNotificationType() {
        return notificationType;
    }
}
