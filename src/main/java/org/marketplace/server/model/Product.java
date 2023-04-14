package org.marketplace.server.model;

import org.marketplace.server.common.Observer;

import java.util.List;

public class Product {

    private List<Observer> observers;
    private final ProductType productType;
    private final double productPrice;

    private final String color;

    private final String yearOfProduction;

    private final ProductCondition productCondition;

    private boolean isAvailable;

    private final User seller;

    public Product(ProductType productType, double productPrice, String yearOfProduction,
                   String color, ProductCondition productCondition, User seller) {
        this.productType = productType;
        this.productPrice = productPrice;
        this.yearOfProduction = yearOfProduction;
        this.color = color;
        this.productCondition = productCondition;
        this.seller = seller;
    }

    public ProductType getProductType() {
        return productType;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getColor() {
        return color;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public ProductCondition getProductCondition() {
        return productCondition;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public User getSeller() {
        return seller;
    }

    public void toggleAvailability() {
        isAvailable = !isAvailable;
    }
}
