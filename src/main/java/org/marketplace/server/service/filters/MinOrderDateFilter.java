package org.marketplace.server.service.filters;

import org.marketplace.server.model.Order;

import java.time.LocalDate;
import java.util.List;

public class MinOrderDateFilter implements Filter<Order> {
    private final LocalDate minDate;

    public MinOrderDateFilter(LocalDate minDate) {
        this.minDate = minDate;
    }

    @Override
    public List<Order> filter(List<Order> items) {
        return items.stream().filter(order -> order.getTimestamp().isAfter(minDate.atStartOfDay())).toList();
    }
}
