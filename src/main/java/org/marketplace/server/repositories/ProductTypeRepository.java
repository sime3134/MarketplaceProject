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

    private static ProductTypeRepository instance;

    private final Database database;

    private ProductTypeRepository () {
        database = Database.getInstance();
    }

    public static ProductTypeRepository getInstance () {
        if(instance == null) {
            instance = new ProductTypeRepository();
        }
        return instance;
    }

    public synchronized List<ProductType> getAllProductTypes() {
        return database.getAllProductTypes();
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
