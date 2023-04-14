package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;

import java.util.List;

public class MaxPriceFilter implements ProductFilter {

    private Double maxPrice;

    public MaxPriceFilter(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductPrice() <= maxPrice).toList();
    }
}
