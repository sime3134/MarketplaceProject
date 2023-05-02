package org.marketplace.server.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.marketplace.server.common.AppConstants;
import org.marketplace.server.model.*;
import org.marketplace.server.model.notifications.Notification;
import org.marketplace.server.service.NotificationService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database class that contains the data we need to
 * run the system, it contains lists with the data
 * and logic to add new data to the files.
 */
public class Database {
    private final ObjectMapper objectMapper;

    private final List<User> userTable;
    private final List<Order> orderTable;
    private final List<Product> productTable;
    private final List<ProductType> productTypeTable;

    public Database() {
        userTable = new ArrayList<>();
        orderTable = new ArrayList<>();
        productTable = new ArrayList<>();
        productTypeTable = new ArrayList<>();

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public void loadDatabase(NotificationService notificationService) {
        new DatabaseLoader(objectMapper, this, notificationService).loadFromFile();
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

    public void setProductAsSold(Product product) {
        product.setProductAsSold();
        try {
            saveListToFile(AppConstants.PRODUCT_TABLE, productTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getUserOrders(User user) {
        return orderTable.stream().filter(order -> order.getBuyer().equals(user)).toList();
    }

    public void addSubscriptionToUser(User user, ProductType productType) {
        user.addSubscription(productType.getId());
        try {
            saveListToFile(AppConstants.USER_TABLE, userTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSubscriber(ProductType productType, User user) {
        productType.addSubscriber(user.getId());
        try {
            saveListToFile(AppConstants.PRODUCT_TYPE_TABLE, productTypeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSubscriptionFromUser(User user, ProductType productType) {
        user.removeSubscription(productType.getId());
        try {
            saveListToFile(AppConstants.USER_TABLE, userTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSubscriberFromProductType(ProductType productType, User user) {
        productType.removeSubscriber(user.getId());
        try {
            saveListToFile(AppConstants.PRODUCT_TYPE_TABLE, productTypeTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
