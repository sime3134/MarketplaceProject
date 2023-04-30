package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;

import java.util.List;

public class MinProductPriceFilter implements Filter<Product> {

    private final Double minPrice;

    public MinProductPriceFilter(Double minPrice) {
        this.minPrice = minPrice;
    }
    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductPrice() >= minPrice).toList();
    }
}
