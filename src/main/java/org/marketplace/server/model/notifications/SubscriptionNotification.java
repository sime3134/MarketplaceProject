package org.marketplace.server.model.notifications;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("subscriptionNotification")
public class SubscriptionNotification extends Notification {

    private static final String MESSAGE = "A product type you have subscribed to have a new product available.";

    public SubscriptionNotification() {
        super(MESSAGE);
    }
}
