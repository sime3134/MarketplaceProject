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
    private final Database database;

    public ProductRepository(Database database) {
        this.database = database;
    }

    public synchronized List<Product> getAllProducts() {
        return database.getProductTable();
    }

    public synchronized Product getProductById(int id) {
        return database.getProductById(id);
    }

    public void addNewProduct(Product product) {
        database.addProduct(product);
    }

    public void setProductAsSold(Product product) {
        database.setProductAsSold(product);
    }
}
