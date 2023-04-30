package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductType;

import java.util.List;

public class ProductTypeFilter implements Filter<Product> {

    private final ProductType productType;

    public ProductTypeFilter(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream().filter(product -> product.getProductType().getId() == productType.getId()).toList();
    }
}
