package com.cat.persistence.mapping;

import org.springframework.util.StringUtils;

public class DefaultNameStrategy implements NameStrategy {

    @Override
    public String name(TableMapping tableMapping) {
        String table = tableMapping.getTable();
        return StringUtils.hasText(table) ? table : StringUtils.uncapitalize(tableMapping.getClazz().getSimpleName());
    }

    @Override
    public String name(ColumnMapping columnMapping) {
        String field = columnMapping.getField();
        String column = columnMapping.getColumn();
        return StringUtils.hasText(column) ? column : StringUtils.uncapitalize(field);
    }
}
