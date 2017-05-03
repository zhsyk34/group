package com.cat.persistence.mapping;

import org.springframework.util.StringUtils;

import java.util.Locale;

public class UnderlineNameStrategy implements NameStrategy {
    private static String underline(String name) {
        final StringBuilder builder = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i < builder.length() - 1; i++) {
            if (Character.isLowerCase(builder.charAt(i - 1)) && Character.isUpperCase(builder.charAt(i)) && Character.isLowerCase(builder.charAt(i + 1))) {
                builder.insert(i++, '_');
            }
        }
        return builder.toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public String name(TableMapping tableMapping) {
        String table = tableMapping.getTable();
        return StringUtils.hasText(table) ? table : underline(tableMapping.getClazz().getSimpleName());
    }

    @Override
    public String name(ColumnMapping columnMapping) {
        String column = columnMapping.getColumn();
        String field = columnMapping.getField();
        Class<?> type = columnMapping.getType();
        if (type == Boolean.class || type == Boolean.TYPE) {
            field = "is" + StringUtils.capitalize(field);
        }
        return StringUtils.hasText(column) ? column : underline(field);
    }
}
