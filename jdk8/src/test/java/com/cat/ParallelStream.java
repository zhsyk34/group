package com.cat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelStream {

    private static final Supplier<Collection<Shop>> supplier = () -> IntStream.rangeClosed(1, 15).mapToObj(Integer::toString).map(Shop::new).collect(toList());
    private static final Executor executor = Executors.newFixedThreadPool(/*Math.min(supplier.get().size(), 100)*/6, r -> {
        Thread thread = new Thread(r);
        thread.setName("test thread");
        thread.setDaemon(true);
        return thread;
    });

    static {
        System.out.println("core size : " + Runtime.getRuntime().availableProcessors());
    }

    public static void main(String[] args) {
//        count(ParallelStream::m1);
//        count(ParallelStream::m2);
//        count(ParallelStream::m3);
        count(ParallelStream::m5);
    }

    public static void count(Action action) {
        long begin = System.nanoTime();
        action.run();
        long end = System.nanoTime();
        System.out.println("\ndone in " + (int) ((end - begin) / 1e6) + " ms");
    }

    public static void m1() {
        supplier.get().stream().map(Shop::getResult).collect(toList());
    }

    public static void m2() {
        supplier.get().parallelStream().map(Shop::getResult).collect(toList());
    }

    public static void m3() {
        List<CompletableFuture<Void>> list = supplier.get().stream().map(shop -> CompletableFuture.runAsync(shop::getResult)).collect(toList());
        list.stream().map(CompletableFuture::join).collect(toList());
    }

    public static void m4() {
        supplier.get().stream().map(shop -> CompletableFuture.runAsync(shop::getResult)).map(CompletableFuture::join).collect(toList());
    }

    public static void m42() {
        supplier.get().stream().map(shop -> CompletableFuture.runAsync(shop::getResult)).parallel().map(CompletableFuture::join).collect(toList());
    }

    public static void m5() {
        List<CompletableFuture<Void>> list = supplier.get().stream().map(shop -> CompletableFuture.runAsync(shop::getResult, executor)).collect(toList());
        list.stream().map(CompletableFuture::join).collect(toList());
    }

    @RequiredArgsConstructor
    private static class Shop {
        @Getter
        private final String name;

        private int getResult() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = new Random().nextInt(100);
            System.out.print(i + " ");
            return i;
        }
    }

}
