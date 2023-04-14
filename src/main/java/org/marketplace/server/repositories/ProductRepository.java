package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductType;

import java.util.List;

public class ProductRepository {

    private Database database;

    public ProductRepository() {
        database = Database.getInstance();
    }

    public List<Product> getAllProducts() {
        return database.getAllProducts();
    }

    public List<ProductType> getAllProductTypes() {
        return database.getAllProductTypes();
    }
}
