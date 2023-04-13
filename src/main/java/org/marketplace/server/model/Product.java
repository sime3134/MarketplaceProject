package org.marketplace.server.model;

import java.util.List;

public class Product {

    private List<Observer> observers;
    private ProductType productType;
    private double productPrice;

    private String color;

    private final String yearOfProduction;

    private final ProductCondition productCondition;

    private boolean isAvailable;

    private final User seller;

    public Product(ProductType productType, double productPrice, String yearOfProduction,
                   ProductCondition productCondition, User seller) {
        this.productType = productType;
        this.productPrice = productPrice;
        this.yearOfProduction = yearOfProduction;
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
