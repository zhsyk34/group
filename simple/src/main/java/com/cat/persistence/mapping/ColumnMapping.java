package com.cat.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public final class ColumnMapping {
    private final String field;
    private final boolean primary;
    private final Class<?> type;
    //TODO:just parse property below
    private final int length;
    private final boolean nullable;
    private final boolean insertable;
    private final boolean updatable;
    private String column;
    private String comment;
}