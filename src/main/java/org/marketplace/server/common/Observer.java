package org.marketplace.server.common;

import org.marketplace.server.common.Observable;

public interface Observer {
    void update(Observable observable);
}
