package org.marketplace.server.service;

import org.marketplace.server.common.exceptions.ProductTypeNotFoundException;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.model.User;
import org.marketplace.server.repositories.ProductTypeRepository;

import java.util.List;

/**
 * Service class that handles ProductType related
 * things such as getting a specific type or
 * getting all product types.
 */
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService() {
        productTypeRepository = ProductTypeRepository.getInstance();
    }

    public ProductType findProductTypeByIdNullIllegal(Integer productTypeId) throws ProductTypeNotFoundException {
        if(productTypeId == null) {
            throw new IllegalArgumentException();
        }

        ProductType productType = productTypeRepository.getProductTypeById(productTypeId);

        if(productType == null) {
            throw new ProductTypeNotFoundException();
        }

        return productType;
    }

    public ProductType findProductTypeById(Integer productTypeId) {
        return productTypeRepository.getProductTypeById(productTypeId);
    }


    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.getAllProductTypes();
    }

    public void addSubscriber(ProductType productType, User user) {
        productTypeRepository.addSubscriber(productType, user);
    }

    public void removeSubscriber(ProductType productType, User user) {
        productTypeRepository.removeSubscriber(productType, user);
    }
}
