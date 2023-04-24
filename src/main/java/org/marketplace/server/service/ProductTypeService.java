package org.marketplace.server.service;

import org.marketplace.server.common.exceptions.ProductTypeNotFoundException;
import org.marketplace.server.model.ProductType;
import org.marketplace.server.repositories.ProductTypeRepository;

import java.util.List;

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
}
