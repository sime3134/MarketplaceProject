package org.marketplace.server.service.filters;

import org.marketplace.server.model.Order;

import java.time.LocalDate;
import java.util.List;

public class MaxOrderDateFilter implements Filter<Order> {

    private final LocalDate maxDate;

    public MaxOrderDateFilter(LocalDate maxDate) {
        this.maxDate = maxDate;
    }

    @Override
    public List<Order> filter(List<Order> items) {
        return items.stream().filter(order -> order.getTimestamp().isBefore(maxDate.plusDays(1).atStartOfDay())).toList();
    }
}
