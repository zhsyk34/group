package com.cat.persistence.mapping;

public interface NameStrategy {

    String name(TableMapping tableMapping);

    String name(ColumnMapping columnMapping);
}
