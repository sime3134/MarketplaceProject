package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.Product;
import org.marketplace.server.model.ProductType;

import java.util.List;

public class ProductRepository {
    private static ProductRepository instance;

    private final Database database;

    private ProductRepository() {
        database = Database.getInstance();
    }

    public static ProductRepository getInstance() {
        if(instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public synchronized List<Product> getAllProducts() {
        return database.getAllProducts();
    }

    public synchronized List<ProductType> getAllProductTypes() {
        return database.getAllProductTypes();
    }

    public synchronized Product getProductById(int id) {
        return database.getProductById(id);
    }
}
