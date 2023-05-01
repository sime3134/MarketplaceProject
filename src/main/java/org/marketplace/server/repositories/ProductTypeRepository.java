package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;

import java.util.List;

/**
 * A singleton class for creating requests to the database concerning the product types.
 *      Functions included are: getting all product types, retreiving product types by ID and subscribing a
 *      notification to the product types.
 */

public class ProductTypeRepository {

    private final Database database;

    public ProductTypeRepository(Database database) {
        this.database = database;
    }

    public synchronized List<ProductType> getAllProductTypes() {
        return database.getProductTypeTable();
    }

    public synchronized ProductType getProductTypeById(Integer id) {
        return database.getProductTypeById(id);
    }

    public void addSubscriber(ProductType productType, User user) {
        database.addSubscriber(productType, user);
    }

    public void removeSubscriber(ProductType productType, User user) {
        database.removeSubscriberFromProductType(productType, user);
    }
}
