package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.Product;

import java.util.List;

/**
 * A singleton class used for handling requests to the database considering products.
 *      the offered functions by this class are: Adding new products, change product availability, get list of
 *      all products and get specific products by ID.
 */

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

    public void toggleProductAvailability(Product product) {
        database.toggleProductAvailability(product);
    }
}
