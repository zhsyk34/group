//package com.cat.jq.entity;
//
//import org.jooq.Field;
//import org.jooq.Schema;
//import org.jooq.Table;
//import org.jooq.TableField;
//import org.jooq.impl.SQLDataType;
//import org.jooq.impl.TableImpl;
//
//public class Cat extends TableImpl<CatRecord> {
//
//    public static final Cat CAT = new Cat();
//    //id
//    public static final TableField<CatRecord, Long> id = createField("id", SQLDataType.BIGINT, CAT);
//
//    //color
//    public static final TableField<CatRecord, String> color = createField("color", SQLDataType.VARCHAR, CAT);
//
//    public Cat() {
//        this("cat", null);
//    }
//
//    public Cat(String name) {
//        this(name, null);
//    }
//
//    public Cat(String name, Schema schema) {
//        this(name, schema, null);
//    }
//
//    public Cat(String name, Schema schema, Table<CatRecord> aliased) {
//        this(name, schema, aliased, null);
//    }
//
//    public Cat(String name, Schema schema, Table<CatRecord> aliased, Field<?>[] parameters) {
//        this(name, schema, aliased, parameters, null);
//    }
//
//    public Cat(String name, Schema schema, Table<CatRecord> aliased, Field<?>[] parameters, String comment) {
//        super(name, schema, aliased, parameters, comment);
//    }
//}
