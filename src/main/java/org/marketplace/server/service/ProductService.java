package org.marketplace.server.service;

import org.marketplace.server.common.exceptions.IllegalProductArgumentException;
import org.marketplace.server.common.exceptions.ProductNotFoundException;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.service.filters.*;
import org.marketplace.server.service.pipelines.ProductPipeline;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    private final ProductValidator productValidator;

    public ProductService() {
        productRepository = ProductRepository.getInstance();
        productValidator = new ProductValidator();
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
                           String color, ProductCondition productCondition) throws IllegalProductArgumentException {

        productValidator.validatePrice(price);
        productValidator.validateYearOfProduction(yearOfProduction);
        productValidator.validateColor(color);

        productRepository.addNewProduct(new Product(productType, price, yearOfProduction, color, productCondition, user));
    }

    public void toggleProductAvailability(int id) throws ProductNotFoundException {
        Product product = findProductById(id);
        productRepository.toggleProductAvailability(product);
    }

    public List<Product> orderByStatus(List<Product> products) {
        List<Product> mutableList = new ArrayList<>(products);
        if(mutableList.isEmpty()) {
            return mutableList;
        }

        mutableList.sort((p1, p2) -> {
            if(p1.isAvailable() && !p2.isAvailable()) {
                return -1;
            }
            if(!p1.isAvailable() && p2.isAvailable()) {
                return 1;
            }
            return 0;
        });
        return mutableList;
    }
}
