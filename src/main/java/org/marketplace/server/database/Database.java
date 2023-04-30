package org.marketplace.server.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.marketplace.server.common.AppConstants;
import org.marketplace.server.model.*;
import org.marketplace.server.model.notifications.Notification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final ObjectMapper objectMapper;
    private static Database instance;

    private final List<User> userTable;
    private final List<Order> orderTable;
    private final List<Product> productTable;
    private final List<ProductType> productTypeTable;

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

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        new DatabaseLoader(objectMapper, this).loadFromFile();
    }

    public synchronized User findUserByUsername(String username) {
        return userTable.stream().filter(user1 -> user1.getUsername().equals(username)).findFirst().orElse(null);
    }

    public synchronized User findUserById(Integer id) {
        return id != null ?userTable.stream().filter(user -> user.getId() == id).findFirst().orElse(null) : null;
    }

    public synchronized void addUser(User newUser) {
        userTable.add(newUser);
        try {
            saveListToFile(AppConstants.USER_TABLE, userTable);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized  <T> void saveListToFile(String filename, List<T> list) throws IOException {
        File file = new File("src/main/resources/database/" + filename);
        objectMapper.writeValue(file, list);
    }

    public List<Product> getAllProducts() {
        return productTable;
    }

    public List<ProductType> getAllProductTypes() {
        return productTypeTable;
    }

    public synchronized Product getProductById(int id) {
        return productTable.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    public synchronized void addOrder(Order order) {
        orderTable.add(order);
        try {
            saveListToFile(AppConstants.ORDER_TABLE, orderTable);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void addProduct(Product product) {
        productTable.add(product);
        try {
            saveListToFile(AppConstants.PRODUCT_TABLE, productTable);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addProductType(ProductType productType) {
        productTypeTable.add(productType);
        try {
            saveListToFile(AppConstants.PRODUCT_TYPE_TABLE, productTypeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserTable() {
        return userTable;
    }

    public List<Order> getOrderTable() {
        return orderTable;
    }

    public List<Product> getProductTable() {
        return productTable;
    }

    public List<ProductType> getProductTypeTable() {
        return productTypeTable;
    }

    public ProductType getProductTypeById(Integer id) {
        return id != null ?
                productTypeTable.stream().filter(productType -> productType.getId() == id).findFirst().orElse(null) : null;
    }

    public void removeOrder(Order order) {
        orderTable.remove(order);
        try {
            saveListToFile(AppConstants.ORDER_TABLE, orderTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNotificationToUser(User user, Notification notification) {
        user.addNotification(notification);
        try {
            saveListToFile(AppConstants.USER_TABLE, userTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Order order, boolean newOrderStatus) {
        order.setOrderStatus(newOrderStatus);
        try {
            saveListToFile(AppConstants.ORDER_TABLE, orderTable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeNotificationFromUser(User user, Integer notificationIndex) {
        user.removeNotification(notificationIndex);
        try {
            saveListToFile(AppConstants.USER_TABLE, userTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleProductAvailability(Product product) {
        product.toggleAvailability();
        try {
            saveListToFile(AppConstants.PRODUCT_TABLE, productTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getUserOrders(User user) {
        return orderTable.stream().filter(order -> order.getBuyer().equals(user)).toList();
    }
}
