package com.cat.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class TableMapping {
    private final Class<?> clazz;
    private String table;
    private String comment;
}
