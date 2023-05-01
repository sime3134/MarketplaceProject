package org.marketplace.server.service;

import org.marketplace.server.common.exceptions.IllegalProductArgumentException;
import org.marketplace.server.common.exceptions.ProductNotFoundException;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.ProductRepository;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.service.filters.*;
import org.marketplace.server.service.pipelines.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class that handles product related events
 * such as building a pipeline and getting a filtered
 * list of products based on chosen parameters, finding
 * a product with its id and adding a new product.
 */
public class ProductService {

    private final ProductRepository productRepository;

    private final FormValidator formValidator;

    public ProductService() {
        productRepository = ProductRepository.getInstance();
        formValidator = new FormValidator();
    }
    public List<Product> getFilteredProducts(ProductType productType, Double minPrice, Double maxPrice,
                                                     String condition) {

        List<Product> allProducts = productRepository.getAllProducts();

        Pipeline<Product> productPipeline = buildProductPipeline(productType, minPrice, maxPrice, condition);

        return productPipeline.execute(allProducts);
    }

    private Pipeline<Product> buildProductPipeline(ProductType productType, Double minPrice, Double maxPrice,
                                                 String condition) {
        Pipeline<Product> productPipeline = new Pipeline<>();

        if(productType != null) {
            productPipeline.addFilter(new ProductTypeFilter(productType));
        }
        if(minPrice != null) {
            productPipeline.addFilter(new MinProductPriceFilter(minPrice));
        }
        if(maxPrice != null) {
            productPipeline.addFilter(new MaxProductPriceFilter(maxPrice));
        }
        if(condition != null) {
            productPipeline.addFilter(new ProductConditionFilter(condition));
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

        formValidator.validatePrice(price);
        formValidator.validateYearOfProduction(yearOfProduction);
        formValidator.validateColor(color);

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
