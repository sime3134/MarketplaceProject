package org.marketplace.server.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    private static Database instance;

    private List<User> userTable;
    private List<Order> orderTable;
    private List<Product> productTable;
    private List<ProductType> productTypeTable;

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        userTable = new ArrayList<>();
        orderTable = new ArrayList<>();
        productTable = new ArrayList<>();
        productTypeTable = new ArrayList<>();
        addMockupData();
    }

    public User findUserByUsername(String username) {
        return userTable.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public User findUserById(String id) {
        return userTable.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean addUser(User newUser) {
        return userTable.add(newUser);
    }

    public void addMockupData() {
        User user1 = new User("Simon", "Jern", "simon.jern@mail.com", LocalDate.now(), "simon.jern", "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user2 = new User("Johan", "Salomonsson", "johan.salomonsson@mail.com", LocalDate.now(), "johan" +
                ".salomonsson", "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user3 = new User("Erik", "Larsson", "erik.larsson@mail.com", LocalDate.now(), "erik.larsson",
                "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user4 = new User("Danny", "Gazic", "dannygazic@gmail.com", LocalDate.now(), "danny.gazic",
                "$2a$12$gzBmZ4zqQ3VmrK/2Jae5cOOjXJVdpzqr92QEgM4M0PYSJFbTjmE7a");
        User user5 = new User("John", "Doe", "john.doe@mail.com", LocalDate.now(), "john.doe", "password");

        userTable.addAll(Arrays.asList(
                user1,
                user2,
                user3,
                user4,
                user5
        ));

        ProductType productType1 = new ProductType("Macbook Pro");
        ProductType productType2 = new ProductType("Macbook Air");
        ProductType productType3 = new ProductType("Macbook");
        ProductType productType4 = new ProductType("Macbook Mini");
        ProductType productType5 = new ProductType("Iphone 11");
        ProductType productType6 = new ProductType("Iphone 11 Pro");
        ProductType productType7 = new ProductType("Iphone 11 Pro Max");
        ProductType productType8 = new ProductType("Iphone 12");
        ProductType productType9 = new ProductType("Samsung Galaxy S23");

        productTypeTable.addAll(Arrays.asList(productType1,
                productType2,
                productType3,
                productType4,
                productType5,
                productType6,
                productType7,
                productType8,
                productType9
        ));

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

        productTable.addAll(Arrays.asList(
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
    }

    public List<Product> getAllProducts() {
        return productTable;
    }

    public List<ProductType> getAllProductTypes() {
        return productTypeTable;
    }
}
