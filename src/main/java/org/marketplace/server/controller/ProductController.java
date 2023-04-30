package org.marketplace.server.controller;

import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;
import org.marketplace.server.common.exceptions.ExceptionWithStatusCode;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductCondition;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;
import org.marketplace.server.model.dto.ErrorResponse;
import org.marketplace.server.service.ProductService;
import org.marketplace.server.service.ProductTypeService;
import org.marketplace.server.service.UserService;

import java.util.List;

public class ProductController {

    private final ProductService productService;

    private final ProductTypeService productTypeService;
    private final UserService userService;

    public ProductController() {
        productService = new ProductService();
        userService = new UserService();
        productTypeService = new ProductTypeService();
    }
    public void getFilteredProducts(Context ctx) {
        Integer productTypeId = ctx.queryParam("productTypeId") != null ? Integer.parseInt(ctx.queryParam(
                "productTypeId")) : null;
        String condition = ctx.queryParam("condition");
        Double minPrice = ctx.queryParam("minPrice") != null ? Double.parseDouble(ctx.queryParam("minPrice")) : null;
        Double maxPrice = ctx.queryParam("maxPrice") != null ? Double.parseDouble(ctx.queryParam("maxPrice")) : null;


        ProductType productType = productTypeService.findProductTypeById(productTypeId);

        List<Product> products = productService.getFilteredProducts(productType, minPrice,
                maxPrice, condition);
        products = productService.orderByStatus(products);
        ctx.header("Content-type", "application/json").json(products);
    }

    public void getAllProductTypes(Context ctx) {
        List<ProductType> productTypes = productTypeService.getAllProductTypes();
        ctx.header("Content-type", "application/json").json(productTypes);
    }

    public void addProduct(Context ctx) {
        Integer productTypeId = ctx.formParam("productTypeId") != null ? Integer.parseInt(ctx.formParam(
                "productTypeId")) : null;
        Double price = ctx.formParam("price") != null ? Double.parseDouble(ctx.formParam("price")) : null;
        String yearOfProduction = ctx.formParam("yearOfProduction");
        String color = ctx.formParam("color");
        String productCondition = ctx.formParam("condition");
        Integer userId = ctx.sessionAttribute("userId") != null ?
                Integer.valueOf(ctx.sessionAttribute("userId")) : null;

        try {
            User user = userService.findUserById(userId);
            ProductType productType = productTypeService.findProductTypeByIdNullIllegal(productTypeId);
            ProductCondition condition = ProductCondition.valueOf(productCondition);

            productService.addProduct(productType, user, price, yearOfProduction, color, condition);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ctx.status(HttpStatus.NOT_FOUND_404).json(new ErrorResponse("Something went wrong trying to add the " +
                    "product"));
        }

    }

    public void getProduct(Context ctx) {
        Integer productId = ctx.pathParam("productId") != null ? Integer.parseInt(ctx.pathParam("productId")) : null;

        try {
            Product product = productService.findProductById(productId);
            ctx.header("Content-type", "application/json").json(product);
        } catch (ExceptionWithStatusCode e) {
            System.out.println(e.getMessage());
            ctx.status(e.getStatus()).json(new ErrorResponse(e.getMessage()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ctx.status(HttpStatus.NOT_FOUND_404).json(new ErrorResponse("Something went wrong trying to get the " +
                    "product"));
        }
    }
}
