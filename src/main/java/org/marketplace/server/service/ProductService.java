package org.marketplace.server.service;

import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductRepository;
import org.marketplace.server.service.exceptions.ProductException;

import java.util.List;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService() {
        productRepository = new ProductRepository();
    }
    public List<Product> getAllProducts() throws ProductException {
        List<Product> allProducts = productRepository.getAllProducts();
        if(allProducts.isEmpty()) {
            throw new ProductException("No products found");
        }
        return allProducts;
    }
}
