package com.cat.util;

import com.cat.persistence.Match;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BeanUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericType(Class<?> clazz, int index) {
        Assert.notNull(clazz, "class can't be null.");
        Assert.state(index >= 0, "index must >= 0");
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return null;
        }

        Type[] types = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= types.length) {
            return null;
        }
        Type type = types[index];

        return type instanceof Class ? (Class<T>) type : null;
    }

    public static <T> Class<T> getGenericType(Class<?> clazz) {
        return getGenericType(clazz, 0);
    }

    /**
     * StackTraceElement
     * example:T->A->B->C ==>[0:C,1:B,2:A,3:T]
     */
    public static int findIndex(StackTraceElement[] elements, String name, Match match, boolean desc) {
        String className;

        if (desc) {
            for (int i = elements.length - 1; i > -1; i--) {
                className = elements[i].getClassName();
                if (match.test(name, className)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < elements.length; i++) {
                className = elements[i].getClassName();
                if (match.test(name, className)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean hasPersistent(Object value) {
        if (value == null) {
            return false;
        }

        Class clazz = value.getClass();
        if (clazz == String.class) {
            return ((String) value).trim().length() > 0;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (Integer) value > 0;
        }
        return (clazz == long.class || clazz == Long.class) && (Long) value > 0;
    }
}
