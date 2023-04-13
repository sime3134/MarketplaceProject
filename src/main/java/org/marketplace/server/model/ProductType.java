package org.marketplace.server.model;

import java.util.ArrayList;
import java.util.List;

public class ProductType implements Observable {
    private String name;
    private List<Observer> observers;

    public ProductType(String name) {
        this.name = name;
        observers = new ArrayList<>();
    }

    public String getName() {
        return name;
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
