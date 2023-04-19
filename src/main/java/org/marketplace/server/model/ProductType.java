package org.marketplace.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.marketplace.server.common.Observable;
import org.marketplace.server.common.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductType implements Observable {
    private final String name;
    private final List<Observer> observers;

    private final int id;

    private static int nextId = 0;

    @JsonCreator
    public ProductType(@JsonProperty("name") String name,
                       @JsonProperty("id") int id) {
        this.name = name;
        observers = new ArrayList<>();
        this.id = id;
    }

    public ProductType(String name) {
        this.name = name;
        observers = new ArrayList<>();
        id = nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
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
}
