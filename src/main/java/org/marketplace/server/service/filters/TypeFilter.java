package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;

import java.util.List;

public class TypeFilter implements ProductFilter {

    private final Integer productTypeId;

    public TypeFilter(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductType().getId() == productTypeId).toList();
    }
}
