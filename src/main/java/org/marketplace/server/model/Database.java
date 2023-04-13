package org.marketplace.server.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;

    private List<User> userTable;
    private List<Order> orderTable;
    private List<Product> productTable;

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
    }

    public User findUserByUsername(String username) {
        return userTable.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public User findUserById(String id) {
        return userTable.stream().filter(user -> user.getId() == Integer.parseInt(id)).findFirst().orElse(null);
    }

    public boolean addUser(User newUser) {
        return userTable.add(newUser);
    }
}
