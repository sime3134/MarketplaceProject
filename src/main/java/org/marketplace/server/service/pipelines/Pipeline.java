package org.marketplace.server.service.pipelines;


import org.marketplace.server.model.Product;
import org.marketplace.server.service.filters.ProductFilter;

import java.util.List;

public interface Pipeline {
    void addFilter(ProductFilter filter);

    List<Product> execute(List<Product> products);
}
