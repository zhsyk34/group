package com.cat.persistence.session;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public abstract class AbstractSession implements Session {

    public abstract String namespace();

    public abstract SqlSession session();

    public <T> T selectOne() {
        return session().selectOne(namespace());
    }

    public <T> T selectOne(Object parameter) {
        return session().selectOne(namespace(), parameter);
    }

    public <E> List<E> selectList() {
        return session().selectList(namespace());
    }

    public <E> List<E> selectList(Object parameter) {
        return session().selectList(namespace(), parameter);
    }

    public <E> List<E> selectList(Object parameter, RowBounds rowBounds) {
        return session().selectList(namespace(), parameter, rowBounds);
    }

    public <K, V> Map<K, V> selectMap(String mapKey) {
        return session().selectMap(namespace(), mapKey);
    }

    public <K, V> Map<K, V> selectMap(Object parameter, String mapKey) {
        return session().selectMap(namespace(), parameter, mapKey);
    }

    public <K, V> Map<K, V> selectMap(Object parameter, String mapKey, RowBounds rowBounds) {
        return session().selectMap(namespace(), parameter, mapKey, rowBounds);
    }

    public int insert() {
        return session().insert(namespace());
    }

    public int insert(Object parameter) {
        return session().insert(namespace(), parameter);
    }

    public int update() {
        return session().update(namespace());
    }

    public int update(Object parameter) {
        return session().update(namespace(), parameter);
    }

    public int delete() {
        return session().delete(namespace());
    }

    public int delete(Object parameter) {
        return session().delete(namespace(), parameter);
    }

}
