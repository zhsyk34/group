package com.cat.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SumTest {

    public static int sum1(int b, int end) {
        int r = 0;
        for (int i = b; i <= end; i++) {
            r += i;
        }
        return r;
    }

    private static void it() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            map.put(i, i);
        }
//
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue() * 3);
//        }
//        System.out.println("_____________");
//
//        map.forEach((k, v) -> System.out.println(k + ":" + v));
//        System.out.println("_____________");
        Function<Integer, Boolean> function = i -> i % 2 == 0;
        map.values().stream().map(function).forEach(SumTest::print);
    }

    private static void print(boolean b) {
        System.out.println(b ? "y" : "n");
    }

    public static int sum2(int begin, int end) {
        return IntStream.rangeClosed(begin, end).parallel().sum();
    }

    public static void main(String[] args) {
//        System.out.println(sum1(1, 10));
//        System.out.println(sum2(1, 10));
        it();

    }

}
