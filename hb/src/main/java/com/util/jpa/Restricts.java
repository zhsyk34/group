package com.util.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class Restricts<T> {

    private final List<T> list = new ArrayList<>();

    public static <T> Restricts<T> instance() {
        return new Restricts<>();
    }

    public static void main(String[] args) {
        Restricts<String> instance = Restricts.instance();
        instance.append("a");
        instance.append("b");
        System.out.println(instance.array());
//        String[] array = instance.array();
//        System.out.println(Arrays.deepToString(array));
    }

    public Restricts append(T t) {
        list.add(t);
        return this;
    }

    public List<T> list() {
        return list;
    }

    @SuppressWarnings("unchecked")
    public T[] array() {
//        Field listField = ReflectionUtils.findField(this.getClass(), "list");
        System.out.println(ResolvableType.forClass(this.getClass()).getGeneric(1).resolve());

//        System.out.println(GenericTypeResolver.resolveTypeArgument(this.getClass(), this.getClass()));

//        System.out.println(ResolvableType.forClass(this.getClass()).getGeneric(0).resolve());

//        Array.newInstance()
//        return (T[]) this.list.toArray();
        return null;
    }
}
