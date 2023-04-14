package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;

import java.util.List;

public class ConditionFilter implements ProductFilter {

    private final ProductCondition condition;
    public ConditionFilter(String condition) {
        this.condition = ProductCondition.valueOf(condition);
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductCondition() == condition).toList();
    }
}
