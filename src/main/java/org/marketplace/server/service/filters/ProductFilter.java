package org.marketplace.server.service.filters;

import org.marketplace.server.model.Product;

import java.util.List;

public interface ProductFilter {
    List<Product> filter(List<Product> products);
}
