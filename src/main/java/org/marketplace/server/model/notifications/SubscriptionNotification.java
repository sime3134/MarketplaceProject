package org.marketplace.server.model.notifications;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A notification type class used to create notifications of type Subscription.
 */

@JsonTypeName("subscriptionNotification")
public class SubscriptionNotification extends Notification {

    public SubscriptionNotification(String productTypeName) {
        super("A product type '" + productTypeName + "', that you have subscribed to have a new product " +
                        "available.",
                "subscriptionNotification");
    }
}
