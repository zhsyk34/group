package com.cat.lambda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MethodRef {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
//        for (int i = 1; i < 10; i++) {
//            map.put(i, String.valueOf(Character.valueOf((char) (i + 50))));
//        }
        map.put(11, "hello");
        System.out.println(map.size());

        map.values().stream().peek(s -> System.out.println(s));

        map.forEach((Apple::new));
        map.forEach(Apple::new);

        LocalDateTime dateTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Apple {
        private int weight;
        private String color;
    }

}
