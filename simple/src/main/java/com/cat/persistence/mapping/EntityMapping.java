package com.cat.persistence.mapping;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@Getter
public final class EntityMapping {
    @NonNull
    private final TableMapping tableMapping;
    //TODO:just only one primary-key
    private final ColumnMapping idColumnMapping;
    @NonNull
    private final List<ColumnMapping> columnMappings;
}
