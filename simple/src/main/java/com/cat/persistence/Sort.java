package com.cat.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Sort {
    private static final Order DEFAULT_ORDER = Order.DESC;
    private final String column;
    private final Order order;

    public static Sort from(String column) {
        return of(column, DEFAULT_ORDER);
    }

    public String column() {
        return column;
    }

    public String order() {
        return order.name();
    }
}
