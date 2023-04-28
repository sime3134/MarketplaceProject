package org.marketplace.server.model.notifications;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("purchaseNotification")
public class PurchaseNotification extends Notification {

    private static final String MESSAGE = "You have a new purchase!";

    private final String buyerUsername;
    private final String productName;

    private final double productPrice;
    private final int orderId;

    public PurchaseNotification(String buyerUsername, String productName, double productPrice, int orderId) {
        super(MESSAGE, "purchaseNotification");
        this.buyerUsername = buyerUsername;
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderId = orderId;
    }

    @JsonCreator
    public PurchaseNotification(@JsonProperty("buyerUsername") String buyerUsername,
                                @JsonProperty("productName") String productName,
                                @JsonProperty("productPrice") double productPrice,
            @JsonProperty("orderId") int orderId,
                                @JsonProperty("message") String message,
                                @JsonProperty("notificationType") String notificationType) {
        super(message, notificationType);
        this.buyerUsername = buyerUsername;
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderId = orderId;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getOrderId() {
        return orderId;
    }
}
