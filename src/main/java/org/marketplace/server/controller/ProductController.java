package org.marketplace.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.ProductService;

import java.util.List;

public class ProductController {

    ProductService productService;
    ObjectMapper objectMapper;

    public ProductController(ObjectMapper objectMapper) {
        productService = new ProductService();
        this.objectMapper = objectMapper;
    }
    public void sendAllProducts(Context ctx) {
        try {
            List<Product> allProducts = productService.getAllProducts();
            String json = objectMapper.writeValueAsString(allProducts);
            System.out.println(json);
            ctx.header("Content-type", "application/json").json(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(HttpStatus.NO_CONTENT_204).json(new ErrorResponse(e.getMessage()));
        }
    }
}
