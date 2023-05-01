package org.marketplace.server.model.notifications;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A notification type class used to create notifications of type OrderStatus.
 */

@JsonTypeName("orderStatusNotification")
public class OrderStatusNotification extends Notification {

    public OrderStatusNotification(String message) {
        super(message, "orderStatusNotification");
    }
    @JsonCreator
    public OrderStatusNotification(@JsonProperty("message") String message, @JsonProperty("notificationType") String notificationType) {
        super(message, notificationType);
    }
}
