package org.marketplace.server.service.pipelines;

import org.marketplace.server.service.filters.Filter;

import java.util.ArrayList;
import java.util.List;

public class Pipeline <T> {

    private final List<Filter<T>> filters;

    public Pipeline() {
        this.filters = new ArrayList<>();
    }

    public void addFilter(Filter<T> filter) {
        filters.add(filter);
    }

    public List<T> execute(List<T> items) {
        List<T> results = items;
        for (Filter<T> filter : filters) {
            results = filter.filter(results);
            if(results.isEmpty()) break; // No need to continue if there are no results (short circuit)
        }

        return results;
    }
}
