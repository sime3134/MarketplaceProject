package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.UserException;
import org.marketplace.server.model.Order;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.common.exceptions.ProductException;
import org.marketplace.server.service.filters.*;
import org.marketplace.server.service.pipelines.ProductPipeline;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        productRepository = ProductRepository.getInstance();
    }
    public List<Product> getFilteredProducts(Integer productTypeId, Double minPrice, Double maxPrice,
                                                     String condition) throws ProductException {
        List<Product> allProducts = productRepository.getAllProducts();

        if(allProducts.isEmpty()) {
            throw new ProductException("No products found", HttpStatus.NO_CONTENT_204);
        }

        ProductPipeline productPipeline = buildProductPipeline(productTypeId, minPrice, maxPrice, condition);

        return productPipeline.execute(allProducts);
    }

    private ProductPipeline buildProductPipeline(Integer productTypeId, Double minPrice, Double maxPrice, String condition) {
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
        return productPipeline;
    }

    public List<ProductType> getAllProductTypes() throws ProductException {
        List<ProductType> allProductTypes = productRepository.getAllProductTypes();
        if(allProductTypes.isEmpty()) {
            throw new ProductException("No product types found", HttpStatus.NO_CONTENT_204);
        }
        return allProductTypes;
    }

    public Product findProductById(Integer productId) {
        return productRepository.getProductById(productId);
    }
}
