package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.IllegalProductArgumentException;

public class ProductValidator {

    public void validatePrice(double price) throws IllegalProductArgumentException {
        if(price < 0.0) {
            throw new IllegalProductArgumentException("Please enter a price greater than 0.",
                    HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateColor(String color) throws IllegalProductArgumentException {
        if(color == null || color.isEmpty()) {
            throw new IllegalProductArgumentException("Please provide a product color.",
                    HttpStatus.BAD_REQUEST_400);
        }
    }

    public void validateYearOfProduction(String yearOfProduction) throws IllegalProductArgumentException {
        if(yearOfProduction == null || yearOfProduction.length() != 4) {
            throw new IllegalProductArgumentException("Please provide a valid year of production, 4 characters " +
                    "long.",
                    HttpStatus.BAD_REQUEST_400);
        }
    }

}
