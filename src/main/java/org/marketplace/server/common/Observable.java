package org.marketplace.server.common;

/**
 * Interface for our observervable class
 *      adds functionality for adding and removing and notifying observers
 */

public interface Observable {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
