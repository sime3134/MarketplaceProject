package org.marketplace.server.service;

import org.marketplace.server.model.Product;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.common.exceptions.ProductException;
import org.marketplace.server.service.filters.ConditionFilter;
import org.marketplace.server.service.filters.MaxPriceFilter;
import org.marketplace.server.service.filters.MinPriceFilter;
import org.marketplace.server.service.filters.TypeFilter;
import org.marketplace.server.service.pipelines.ProductPipeline;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        productRepository = new ProductRepository();
    }
    public List<Product> getFilteredProducts(Integer productTypeId, Double minPrice, Double maxPrice,
                                             String condition) throws ProductException {
        List<Product> allProducts = productRepository.getAllProducts();

        if(allProducts.isEmpty()) {
            throw new ProductException("No products found");
        }

        ProductPipeline productPipeline = new ProductPipeline();

        if(productTypeId != null) {
            productPipeline.addFilter(new TypeFilter(productTypeId));
        }
        if(minPrice != null) {
            productPipeline.addFilter(new MinPriceFilter(minPrice));
        }
        if(maxPrice != null) {
            productPipeline.addFilter(new MaxPriceFilter(maxPrice));
        }
        if(condition != null) {
            productPipeline.addFilter(new ConditionFilter(condition));
        }

        return productPipeline.execute(allProducts);
    }

    public List<ProductType> getAllProductTypes() throws ProductException {
        List<ProductType> allProductTypes = productRepository.getAllProductTypes();
        if(allProductTypes.isEmpty()) {
            throw new ProductException("No product types found");
        }
        return allProductTypes;
    }
}
