package org.marketplace.server.model;

import java.util.List;

public class ProductRepository {

    private Database database;

    public ProductRepository() {
        database = Database.getInstance();
    }

    public List<Product> getAllProducts() {
        return database.getAllProducts();
    }
}
