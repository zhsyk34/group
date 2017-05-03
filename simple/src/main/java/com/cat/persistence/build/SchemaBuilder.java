package com.cat.persistence.build;

import com.cat.persistence.mapping.ColumnMapping;
import com.cat.persistence.mapping.EntityMapping;
import com.cat.persistence.mapping.EntityMappingManager;
import com.cat.persistence.mapping.TableMapping;
import com.cat.util.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cat.persistence.build.SchemaBuilder.NumericalEnum.*;

/**
 * https://dev.mysql.com/doc/refman/5.7/en/create-table.html
 */
public class SchemaBuilder {

    private static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS ${table}";
    private static final String CREATE_DEFINITION = "(\n${definition})";
    private static final String TABLE_OPTIONS = tableOptions();
    private static final String COMMENT = "COMMENT";

    private static final String template = CREATE_COMMAND + CREATE_DEFINITION + TABLE_OPTIONS;

    private static final String SPACE = "    ";
    private static final String LINE_SPLIT = ",\n";
    private static final String NO_NULL = "NOT NULL";
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String UNSIGNED = "UNSIGNED";
    private static final String PK = "PRIMARY KEY (${id})";
    private static boolean NUMBER_UNSIGNED = true;

    public static void main(String[] args) {
        build("com.cat.entity");
//        build(Student.class);
    }

    public static void build(String packageName) {
//        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
//        provider.addIncludeFilter();
//        Set<BeanDefinition> beans = provider.findCandidateComponents("com.cat.entity");
        Set<Class<?>> set = FileUtils.scanPackage(packageName);
        Optional.ofNullable(set).ifPresent(classes -> classes.forEach(SchemaBuilder::build));
    }

    public static String build(Class<?> entity) {
        Map<String, String> map = new HashMap<>();
        EntityMapping entityMapping = EntityMappingManager.from(entity);
        if (entityMapping == null) {
            return null;
        }
        TableMapping tableMapping = entityMapping.getTableMapping();
        map.put("table", tableMapping.getTable());

        String columns = entityMapping.getColumnMappings().stream().map(SchemaBuilder::columnDefinition).collect(Collectors.joining());
        map.put("definition", columns + idDefinition(entityMapping.getIdColumnMapping().getColumn()));

        String tableComment = tableMapping.getComment();
        String sql = StrSubstitutor.replace(template, map);
        //table comment
        if (StringUtils.hasText(tableComment)) {
            sql += "\n" + COMMENT + "'" + tableComment + "'";
        }
        sql += ";\n\n";

        System.out.println(sql);
        return sql;
    }

    /**
     * column_definition:
     * data_type [NOT NULL | NULL] [DEFAULT default_value]
     * [AUTO_INCREMENT] [UNIQUE [KEY] | [PRIMARY] KEY]
     * [COMMENT 'string']
     * [COLUMN_FORMAT {FIXED|DYNAMIC|DEFAULT}]
     * [STORAGE {DISK|MEMORY|DEFAULT}]
     * [reference_definition]
     * | data_type [GENERATED ALWAYS] AS (expression)
     * [VIRTUAL | STORED] [UNIQUE [KEY]] [COMMENT comment]
     * [NOT NULL | NULL] [[PRIMARY] KEY]
     */
    private static String columnDefinition(ColumnMapping columnMapping) {
        StringBuilder builder = new StringBuilder();
        builder.append(columnMapping.getColumn());
        builder.append(SPACE).append(columnType(columnMapping));
        if (!columnMapping.isNullable()) {
            builder.append(SPACE).append(NO_NULL);
        }
        if (columnMapping.isPrimary()) {
            builder.append(SPACE).append(AUTO_INCREMENT);
        }
        String comment = columnMapping.getComment();
        if (StringUtils.hasText(comment)) {
            builder.append(SPACE).append(COMMENT).append(" '").append(comment).append("'");
        }
        builder.append(LINE_SPLIT);

        return builder.toString();
    }

    private static String idDefinition(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        return StrSubstitutor.replace(PK, map);
    }

    private static NumericalEnum from(Class<?> clazz) {
        if (clazz == Boolean.class || clazz == boolean.class) {
            return TINYINT;
        }
        //enum
        if (clazz.isEnum()) {
            return TINYINT;
        }
        if (clazz == short.class || clazz == Short.class) {
            return SMALLINT;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return INT;
        }
        if (clazz == long.class || clazz == Long.class) {
            return BIGINT;
        }
        if (clazz == float.class || clazz == Float.class) {
            return FLOAT;
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return DECIMAL;
        }

        return null;
    }

    //https://dev.mysql.com/doc/refman/5.7/en/integer-types.html
    private static String columnType(ColumnMapping columnMapping) {
        Class<?> clazz = columnMapping.getType();

        NumericalEnum numericalEnum = from(clazz);
        if (numericalEnum != null) {
            return numericalEnum + (NUMBER_UNSIGNED ? SPACE + UNSIGNED : "") + SPACE + NO_NULL;//length
        }
        if (clazz == Date.class || clazz == java.sql.Date.class || clazz == LocalDate.class || clazz == LocalDateTime.class) {
            return TimeEnum.TIMESTAMP.toString();
        }
        //string
        int length = columnMapping.getLength();//TODO
        return CharEnum.VARCHAR.toString() + "(" + length + ")";
    }

    /**
     * table_option:
     * ENGINE [=] engine_name
     * | AUTO_INCREMENT [=] value
     * | AVG_ROW_LENGTH [=] value
     * | [DEFAULT] CHARACTER SET [=] charset_name
     * | CHECKSUM [=] {0 | 1}
     * | [DEFAULT] COLLATE [=] collation_name
     * | COMMENT [=] 'string'
     * | COMPRESSION [=] {'ZLIB'|'LZ4'|'NONE'}
     * | CONNECTION [=] 'connect_string'
     * | DATA DIRECTORY [=] 'absolute path to directory'
     * | DELAY_KEY_WRITE [=] {0 | 1}
     * | ENCRYPTION [=] {'Y' | 'N'}
     * | INDEX DIRECTORY [=] 'absolute path to directory'
     * | INSERT_METHOD [=] { NO | FIRST | LAST }
     * | KEY_BLOCK_SIZE [=] value
     * | MAX_ROWS [=] value
     * | MIN_ROWS [=] value
     * | PACK_KEYS [=] {0 | 1 | DEFAULT}
     * | PASSWORD [=] 'string'
     * | ROW_FORMAT [=] {DEFAULT|DYNAMIC|FIXED|COMPRESSED|REDUNDANT|COMPACT}
     * | STATS_AUTO_RECALC [=] {DEFAULT|0|1}
     * | STATS_PERSISTENT [=] {DEFAULT|0|1}
     * | STATS_SAMPLE_PAGES [=] value
     * | TABLESPACE tablespace_name [STORAGE {DISK|MEMORY|DEFAULT}]
     * | UNION [=] (tbl_name[,tbl_name]...)
     */
    private static String tableOptions() {
        //https://dev.mysql.com/doc/refman/5.7/en/storage-engines.html
        return Stream.of(
                "ENGINE = InnoDB",
                "AUTO_INCREMENT = 1",
                "DEFAULT CHARACTER SET = UTF8"// StandardCharsets.UTF_8
        ).collect(Collectors.joining("\n"));
    }

    //https://dev.mysql.com/doc/refman/5.7/en/integer-types.html
    @SuppressWarnings("unused")
    enum NumericalEnum {
        TINYINT, SMALLINT, MEDIUMINT, INT, BIGINT,
        REAL, DOUBLE, FLOAT, DECIMAL, NUMERIC
    }

    @SuppressWarnings("unused")
    enum CharEnum {
        CHAR, VARCHAR, TEXT
    }

    @SuppressWarnings("unused")
    enum TimeEnum {
        DATE, TIME, TIMESTAMP, DATETIME
    }
}
