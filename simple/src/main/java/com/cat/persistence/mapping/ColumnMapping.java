package com.cat.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Getter
public final class ColumnMapping {
    private final String field;
    private final boolean primary;
    private final Class<?> type;
    //TODO:just parse property below
    private final int length;
    private final boolean nullable;
    private final boolean insertable;
    private final boolean updatable;
    @Setter
    private String column;
    @Setter
    private String comment;
}