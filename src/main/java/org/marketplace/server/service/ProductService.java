package org.marketplace.server.service;

import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.ProductNotFoundException;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.common.exceptions.ProductException;
import org.marketplace.server.service.filters.*;
import org.marketplace.server.service.pipelines.ProductPipeline;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        productRepository = ProductRepository.getInstance();
    }
    public List<Product> getFilteredProducts(ProductType productType, Double minPrice, Double maxPrice,
                                                     String condition) {

        List<Product> allProducts = productRepository.getAllProducts();

        ProductPipeline productPipeline = buildProductPipeline(productType, minPrice, maxPrice, condition);

        return productPipeline.execute(allProducts);
    }

    private ProductPipeline buildProductPipeline(ProductType productType, Double minPrice, Double maxPrice,
                                                 String condition) {
        ProductPipeline productPipeline = new ProductPipeline();

        if(productType != null) {
            productPipeline.addFilter(new TypeFilter(productType));
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

    public Product findProductById(Integer productId) throws ProductNotFoundException {
        Product product = productRepository.getProductById(productId);

        if(product == null) {
            throw new ProductNotFoundException();
        }
        return product;
    }

    public void addProduct(ProductType productType, User user, Double price, String yearOfProduction,
                           String color, ProductCondition productCondition) {;
        productRepository.addNewProduct(new Product(productType, price, yearOfProduction, color, productCondition, user));
    }
}
