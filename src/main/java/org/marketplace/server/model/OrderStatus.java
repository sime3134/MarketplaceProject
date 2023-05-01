package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Class used to handle the Order status of a specific order
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    ;

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    /**
     * Description value of order status.
     *
     * @param status the status
     * @return the order status
     */
    public static OrderStatus descriptionValueOf(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getStatus().equals(status)) {
                return orderStatus;
            }
        }
        return null;
    }
}
