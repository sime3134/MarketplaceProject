package org.marketplace.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.ProductService;
import org.marketplace.server.service.exceptions.ProductException;

import java.util.List;

public class ProductController {

    ProductService productService;

    public ProductController() {
        productService = new ProductService();
    }
    public void sendAllProducts(Context ctx) {
        try {
            List<Product> allProducts = productService.getAllProducts();
            ctx.header("Content-type", "application/json").json(allProducts);
        } catch (ProductException e) {
            ctx.status(HttpStatus.NO_CONTENT_204).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void sendAllProductTypes(Context ctx) {
        try {
            List<ProductType> productTypes = productService.getAllProductTypes();
            ctx.header("Content-type", "application/json").json(productTypes);
        } catch (ProductException e) {
            ctx.status(HttpStatus.NO_CONTENT_204).json(new ErrorResponse(e.getMessage()));
        }
    }
}
