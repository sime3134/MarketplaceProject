package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.Product;

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

    public synchronized Product getProductById(int id) {
        return database.getProductById(id);
    }

    public void addNewProduct(Product product) {
        database.addProduct(product);
    }
}
