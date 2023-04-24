package org.marketplace.server.repositories;

import org.marketplace.server.database.Database;
import org.marketplace.server.model.ProductType;

import java.util.List;

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
}
