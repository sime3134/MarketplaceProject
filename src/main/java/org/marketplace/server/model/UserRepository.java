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

    public void findUserByUsername(String username) {
        database.findUserByUsername(username);
    }

    public void findUserById(String id) {
        database.findUserById(id);
    }
}
