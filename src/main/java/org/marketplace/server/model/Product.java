package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class for creating a product
 */

public class Product {
    private final ProductType productType;
    private final double productPrice;

    private final String color;

    private final String yearOfProduction;

    private final ProductCondition productCondition;

    private boolean isAvailable;

    private final User seller;

    private final int id;

    private static int nextId;

    public Product(ProductType productType, double productPrice, String yearOfProduction,
                   String color, ProductCondition productCondition, User seller) {
        this.productType = productType;
        this.productPrice = productPrice;
        this.yearOfProduction = yearOfProduction;
        this.color = color;
        this.productCondition = productCondition;
        this.seller = seller;
        this.id = nextId++;
        this.isAvailable = true;
    }

    public Product(int id, ProductType productType, double productPrice, String yearOfProduction,
                   String color, ProductCondition productCondition, User seller, boolean isAvailable) {
        this.productType = productType;
        this.productPrice = productPrice;
        this.yearOfProduction = yearOfProduction;
        this.color = color;
        this.productCondition = productCondition;
        this.seller = seller;
        this.id = id;
        this.isAvailable = isAvailable;
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

    public void setProductAsSold() {
        isAvailable = false;
    }

    @JsonIgnoreProperties({"hashedPassword", "emailAddress", "dateOfBirth", "notifications"})
    public User getSeller() {
        return seller;
    }

    public int getId() {
        return id;
    }

    /**
     * increments to add unique ID to product
     *
     * @param maxId the max id
     */
    public static void updateNextId(int maxId) {
        if (maxId >= nextId) {
            nextId = maxId + 1;
        }
    }
}
