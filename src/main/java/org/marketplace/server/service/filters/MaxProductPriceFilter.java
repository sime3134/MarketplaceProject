package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;

import java.util.List;

public class MaxProductPriceFilter implements Filter<Product> {

    private final Double maxPrice;

    public MaxProductPriceFilter(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductPrice() <= maxPrice).toList();
    }
}
