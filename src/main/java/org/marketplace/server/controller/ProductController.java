package org.marketplace.server.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.ProductService;

import java.util.ArrayList;
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
        } catch (Exception e) {
            ctx.status(HttpStatus.NOT_FOUND).json(new ErrorResponse(e.getMessage()));
        }
    }
}
