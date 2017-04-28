package com.cat.jq.entity;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.Table;
import org.jooq.impl.UpdatableRecordImpl;

public class CatRecord extends UpdatableRecordImpl<CatRecord> implements Record2<Long, String> {
    public CatRecord(Table<CatRecord> table) {
        super(table);
    }

    @Override
    public Field<Long> field1() {
        return Cat.id;
    }

    @Override
    public Field<String> field2() {
        return Cat.color;
    }

    @Override
    public Long value1() {
        return (Long) get(0);
    }

    @Override
    public String value2() {
        return (String) get(1);
    }

    @Override
    public Record2<Long, String> value1(Long value) {
        setValue(Cat.id, value);
        return this;
    }

    @Override
    public Record2<Long, String> value2(String value) {
        setValue(Cat.color, value);
        return this;
    }

    @Override
    public Record2<Long, String> values(Long aLong, String s) {
        return this.value1(aLong).value2(s);
    }

    @Override
    public Row2<Long, String> valuesRow() {
        return super.valuesRow();
    }
}
