package org.marketplace.server.model;

import org.marketplace.server.common.Observable;
import org.marketplace.server.common.Observer;

import java.util.ArrayList;
import java.util.List;

public class ProductType implements Observable {
    private final String name;
    private final List<Observer> observers;

    private final int id;

    private static int nextId = 0;

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
}
