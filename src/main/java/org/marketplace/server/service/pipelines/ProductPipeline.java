package org.marketplace.server.service.pipelines;

import org.marketplace.server.model.Product;
import org.marketplace.server.service.filters.ProductFilter;

import java.util.ArrayList;
import java.util.List;

public class ProductPipeline implements Pipeline {

    private final List<ProductFilter> filters;

    public ProductPipeline() {
        filters = new ArrayList<>();
    }

    @Override
    public void addFilter(ProductFilter filter) {
        filters.add(filter);
    }

    @Override
    public List<Product> execute(List<Product> products) {
        List<Product> results = products;
        for (ProductFilter filter : filters) {
            results = filter.filter(results);
        }

        return results;
    }
}
