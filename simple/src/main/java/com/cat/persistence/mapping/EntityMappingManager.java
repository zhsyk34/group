package com.cat.persistence.mapping;

import com.cat.persistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityMappingManager {

    private static final Map<Class<?>, EntityMapping> ENTITY_MAPPING_MAP = new ConcurrentHashMap<>();
    private static final NameStrategy NAME_STRATEGY = new UnderlineNameStrategy();

    public static EntityMapping from(Class<?> clazz) {
        EntityMapping result = ENTITY_MAPPING_MAP.get(clazz);
        if (result != null) {
            return result;
        }

        //1.1:table
        Entity entity = clazz.getAnnotation(Entity.class);
        if (entity == null) {
            //System.err.println("not an mapping entity!");
            return null;
        }

        //1.2:comment
        Comment table = clazz.getAnnotation(Comment.class);
        String comment = table == null ? null : table.value();

        TableMapping tableMapping = TableMapping.of(clazz, entity.name(), comment);
        //1.3:post
        tableMapping.setTable(NAME_STRATEGY.name(tableMapping));

        //2.1:fields
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

        //2.2:super fields
        Class<?> superclass = clazz.getSuperclass();
        while (true) {
            if (superclass == null || superclass == Object.class || !superclass.isAnnotationPresent(MappedSuperclass.class)) {
                break;
            }

            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        //3.1:columns
        List<ColumnMapping> columnMappings = fields.stream().map(EntityMappingManager::parseField).sorted((c1, c2) -> c1.isPrimary() ? -1 : c2.isPrimary() ? 1 : 0).collect(Collectors.toList());

        //System.out.println(columnMappings.stream().map(ColumnMapping::getColumn).collect(Collectors.joining(", ")));

        //3.2:id only one?
        ColumnMapping idColumnMapping = columnMappings.stream().filter(ColumnMapping::isPrimary).findFirst().orElse(null);
        if (idColumnMapping == null) {
            return null;
        }

        //4:entity mapping
        result = EntityMapping.of(tableMapping, idColumnMapping, columnMappings);
        ENTITY_MAPPING_MAP.put(clazz, result);
        return result;
    }

    private static ColumnMapping parseField(Field field) {
        Column column = field.getAnnotation(Column.class);
        String columnName = Optional.ofNullable(column).map(Column::name).orElse(null);

        boolean primary = field.isAnnotationPresent(Id.class);
        boolean insertable = !primary && (column == null || column.insertable());
        boolean updatable = !primary && (column == null || column.updatable());
        boolean nullable = primary || column == null || column.nullable();
        int length = column == null ? 255 : column.length();
        String comment = Optional.ofNullable(field.getAnnotation(Comment.class)).map(Comment::value).orElse(null);
        ColumnMapping columnMapping = ColumnMapping.of(field.getName(), columnName, primary, field.getType(), length, nullable, insertable, updatable, comment);
        //post
        columnMapping.setColumn(NAME_STRATEGY.name(columnMapping));
        return columnMapping;
    }

}
