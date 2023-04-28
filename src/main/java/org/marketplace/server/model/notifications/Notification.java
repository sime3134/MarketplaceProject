package org.marketplace.server.model.notifications;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PurchaseNotification.class, name = "purchaseNotification"),
        @JsonSubTypes.Type(value = SubscriptionNotification.class, name = "subscriptionNotification")
})
public abstract class Notification {
    private final String message;

    protected Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
