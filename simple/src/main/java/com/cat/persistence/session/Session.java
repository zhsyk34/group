package com.cat.persistence.session;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface Session {

    SqlSession session();

    <T> T selectOne();

    <T> T selectOne(Object parameter);

    <T> List<T> selectList();

    <T> List<T> selectList(Object parameter);

    <T> List<T> selectList(Object parameter, RowBounds rowBounds);

    <K, V> Map<K, V> selectMap(String mapKey);

    <K, V> Map<K, V> selectMap(Object parameter, String mapKey);

    <K, V> Map<K, V> selectMap(Object parameter, String mapKey, RowBounds rowBounds);

    int insert();

    int insert(Object parameter);

    int update();

    int update(Object parameter);

    int delete();

    int delete(Object parameter);
}
