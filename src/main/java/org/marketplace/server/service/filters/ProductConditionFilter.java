package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;

import java.util.List;

public class ProductConditionFilter implements Filter<Product> {
    private final ProductCondition condition;
    public ProductConditionFilter(String condition) {
        this.condition = ProductCondition.valueOf(condition);
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductCondition() == condition).toList();
    }
}
