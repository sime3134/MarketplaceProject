package org.marketplace.server.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.marketplace.server.common.AppConstants;
import org.marketplace.server.model.*;
import org.marketplace.server.model.deserializers.OrderDeserializer;
import org.marketplace.server.model.deserializers.ProductDeserializer;
import org.marketplace.server.model.notifications.PurchaseNotification;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DatabaseLoader {

    private final ObjectMapper objectMapper;
    private final Database database;

    public DatabaseLoader(ObjectMapper objectMapper, Database database) {
        this.objectMapper = objectMapper;
        this.database = database;
    }

    public void loadFromFile() {
        try {
            loadUserListFromFile("src/main/resources/database/" + AppConstants.USER_TABLE);
            loadProductTypeListFromFile("src/main/resources/database/" + AppConstants.PRODUCT_TYPE_TABLE);

            SimpleModule productModel = new SimpleModule();
            productModel.addDeserializer(Product.class, new ProductDeserializer(database.getUserTable(),
                    database.getProductTypeTable()));
            objectMapper.registerModule(productModel);
            loadProductListFromFile("src/main/resources/database/" + AppConstants.PRODUCT_TABLE);

            SimpleModule orderModule = new SimpleModule();
            orderModule.addDeserializer(Order.class, new OrderDeserializer(database.getUserTable(), database.getProductTable()));
            objectMapper.registerModule(orderModule);
            loadOrderListFromFile("src/main/resources/database/" + AppConstants.ORDER_TABLE);

            if (database.getUserTable().isEmpty()) {
                addMockupData();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMockupData() {
        User user1 = new User("Simon", "Jern", "simon.jern@mail.com", LocalDate.now(), "simon.jern", "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user2 = new User("Johan", "Salomonsson", "johan.salomonsson@mail.com", LocalDate.now(), "johan" +
                ".salomonsson", "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user3 = new User("Erik", "Larsson", "erik.larsson@mail.com", LocalDate.now(), "erik.larsson",
                "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user4 = new User("Danny", "Gazic", "dannygazic@gmail.com", LocalDate.now(), "danny.gazic",
                "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user5 = new User("John", "Doe", "john.doe@mail.com", LocalDate.now(), "john.doe", "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");

        database.getUserTable().addAll(Arrays.asList(
                user1,
                user2,
                user3,
                user4,
                user5
        ));

        try {
            database.saveListToFile(AppConstants.USER_TABLE, database.getUserTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProductType productType1 = new ProductType("Macbook Pro");
        ProductType productType2 = new ProductType("Macbook Air");
        ProductType productType3 = new ProductType("Macbook");
        ProductType productType4 = new ProductType("Macbook Mini");
        ProductType productType5 = new ProductType("Iphone 11");
        ProductType productType6 = new ProductType("Iphone 11 Pro");
        ProductType productType7 = new ProductType("Iphone 11 Pro Max");
        ProductType productType8 = new ProductType("Iphone 12");
        ProductType productType9 = new ProductType("Samsung Galaxy S23");

        database.getProductTypeTable().addAll(Arrays.asList(productType1,
                productType2,
                productType3,
                productType4,
                productType5,
                productType6,
                productType7,
                productType8,
                productType9
        ));

        try {
            database.saveListToFile(AppConstants.PRODUCT_TYPE_TABLE, database.getProductTypeTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Product product1 = new Product(productType1, 8900, "2018", "White", ProductCondition.GOOD, user4);
        Product product2 = new Product(productType1, 2500, "2018", "Black", ProductCondition.NOT_WORKING, user3);
        Product product3 = new Product(productType5, 1000, "2019", "Grey", ProductCondition.NOT_WORKING, user1);
        Product product4 = new Product(productType7, 6500, "2018", "Red", ProductCondition.GOOD, user3);
        Product product5 = new Product(productType8, 30000, "2021", "Gold", ProductCondition.VERY_GOOD, user5);
        Product product6 = new Product(productType9, 6300, "2023", "Black", ProductCondition.GOOD, user2);
        Product product7 = new Product(productType9, 10000, "2023", "White", ProductCondition.VERY_GOOD, user4);
        Product product8 = new Product(productType2, 6000, "2018", "White", ProductCondition.GOOD, user4);
        Product product9 = new Product(productType3, 2000, "2016", "White", ProductCondition.NOT_WORKING, user1);
        Product product10 = new Product(productType6, 7500, "2021", "Black", ProductCondition.GOOD, user5);

        database.getProductTable().addAll(Arrays.asList(
                product1,
                product2,
                product3,
                product4,
                product5,
                product6,
                product7,
                product8,
                product9,
                product10
        ));

        try {
            database.saveListToFile(AppConstants.PRODUCT_TABLE, database.getProductTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUserListFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return;
        }
        List<User> list = objectMapper.readValue(file, new TypeReference<>() {});
        int maxId = list.stream().mapToInt(User::getId).max().orElse(-1);
        User.updateNextId(maxId);
        database.getUserTable().addAll(list);
    }

    private void loadProductListFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        List<Product> list = objectMapper.readValue(file, new TypeReference<>() {});
        int maxId = list.stream().mapToInt(Product::getId).max().orElse(-1);
        Product.updateNextId(maxId);
        database.getProductTable().addAll(list);
    }

    private void loadProductTypeListFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        List<ProductType> list = objectMapper.readValue(file, new TypeReference<>() {});
        int maxId =list.stream().mapToInt(ProductType::getId).max().orElse(-1);
        ProductType.updateNextId(maxId);
        database.getProductTypeTable().addAll(list);
    }

    private void loadOrderListFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        List<Order> list = objectMapper.readValue(file, new TypeReference<>() {});
        int maxId = list.stream().mapToInt(Order::getId).max().orElse(-1);
        Order.updateNextId(maxId);
        database.getOrderTable().addAll(list);
    }
}
