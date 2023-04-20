package org.marketplace.server.controller;

import io.javalin.http.Context;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.OrderService;
import org.marketplace.server.service.ProductService;
import org.marketplace.server.common.exceptions.ProductException;
import org.marketplace.server.service.UserService;

import java.util.List;

public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    private final OrderService orderService;

    public ProductController() {
        productService = new ProductService();
        userService = new UserService();
        orderService = new OrderService();
    }
    public void getFilteredProducts(Context ctx) {
        Integer productTypeId = ctx.queryParam("productTypeId") != null ? Integer.parseInt(ctx.queryParam(
                "productTypeId")) : null;
        String condition = ctx.queryParam("condition");
        Double minPrice = ctx.queryParam("minPrice") != null ? Double.parseDouble(ctx.queryParam("minPrice")) : null;
        Double maxPrice = ctx.queryParam("maxPrice") != null ? Double.parseDouble(ctx.queryParam("maxPrice")) : null;

        try {
            List<Product> filteredProducts = productService.getFilteredProducts(productTypeId, minPrice,
                    maxPrice, condition);
            ctx.header("Content-type", "application/json").json(filteredProducts);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }

    public void getAllProductTypes(Context ctx) {
        try {
            List<ProductType> productTypes = productService.getAllProductTypes();
            ctx.header("Content-type", "application/json").json(productTypes);
        } catch (ProductException e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        }
    }
}
