package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.marketplace.server.common.Observable;
import org.marketplace.server.common.Observer;
import org.marketplace.server.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ProductType implements Observable {
    private final String name;
    private static final List<Observer> observers = List.of(NotificationService.getInstance());

    private final List<Integer> subscribers;

    private final int id;

    private static int nextId = 0;

    @JsonCreator
    public ProductType(@JsonProperty("name") String name,
                       @JsonProperty("subscribers") List<Integer> subscribers,
                       @JsonProperty("id") int id) {
        this.name = name;
        this.subscribers = subscribers != null ? subscribers : new ArrayList<>();
        this.id = id;
    }

    public ProductType(String name) {
        this.name = name;
        subscribers = new ArrayList<>();
        id = nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getSubscribers() {
        return subscribers;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public static void updateNextId(int maxId) {
        if (maxId >= nextId) {
            nextId = maxId + 1;
        }
    }

    public void addSubscriber(Integer userId) {
        subscribers.add(userId);
    }

    public void removeSubscriber(Integer id) {
        subscribers.remove(id);
    }
}
