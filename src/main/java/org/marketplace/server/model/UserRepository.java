package org.marketplace.server.model;

public class UserRepository {
    private static UserRepository instance;

    private Database database;
    private UserRepository() {
        database = Database.getInstance();
    }
    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findUserByUsername(String username) {
        return database.findUserByUsername(username);
    }

    public User findUserById(String id) {
        return database.findUserById(id);
    }

    public boolean addUser(User newUser) {
        return database.addUser(newUser);
    }
}
