package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;

import java.util.List;

public class MinPriceFilter implements ProductFilter {

    private Double minPrice;

    public MinPriceFilter(Double minPrice) {
        this.minPrice = minPrice;
    }
    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductPrice() >= minPrice).toList();
    }
}
