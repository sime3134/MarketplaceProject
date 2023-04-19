package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProductCondition {

    NEW("New"),
    VERY_GOOD("Very Good"),
    GOOD("Good"),
    NOT_WORKING("Not Working Properly");

    private final String description;

    ProductCondition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override public String toString() {
        return description;
    }

    public static ProductCondition descriptionValueOf(String description) {
        for (ProductCondition productCondition : ProductCondition.values()) {
            if (productCondition.getDescription().equals(description)) {
                return productCondition;
            }
        }
        return null;
    }

}
