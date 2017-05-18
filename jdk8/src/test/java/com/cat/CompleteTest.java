package com.cat;

import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CompleteTest {

    @Test
    public void when() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.completedFuture(47);
        CompletableFuture<Integer> future1 = future.whenComplete((r, e) -> {
            System.out.println(r);
            Optional.ofNullable(e).ifPresent(Throwable::printStackTrace);
        });

        System.out.println(future1.get());
    }

    @Test
    public void exception() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<Integer> future1 = future.exceptionally(e -> -1);

        System.out.println(future1.get());

    }

    @Test
    public void exception2() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 1 / 0);
        CompletableFuture<Integer> future1 = future.exceptionally(e -> -1);

        System.out.println(future1.get());
    }

    @Test
    public void then() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<Integer> future1 = future.thenApply(i -> i * 4);

        System.out.println(future1.get());
    }

    @Test
    public void then0() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<String> future1 = future.thenApply(i -> i + "a");

        System.out.println(future1.get());
    }

    @Test
    public void then2() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<Integer> future1 = future.thenApplyAsync(i -> i * 4);

        System.out.println(future1.get());
    }

    @Test
    public void then3() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 0);
        CompletableFuture<Integer> future1 = future.thenApplyAsync(i -> i * 4);

        System.out.println(future1.get());
    }

    @Test
    public void accept() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<Void> future1 = future.thenAcceptAsync(System.out::println);

        System.out.println(future1.get());
    }

    @Test
    public void accept2() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<Void> future1 = future.thenAcceptBoth(CompletableFuture.supplyAsync(() -> "other"), (i, s) -> System.out.println(i + s));

        System.out.println(future1.get());
    }

    @Test
    public void compose() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<Integer> future1 = future.thenCompose(i -> {
            System.out.println(i);
            return CompletableFuture.supplyAsync(() -> i * 7);
        });

        System.out.println(future1.get());
    }

    @Test
    public void combine() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10 / 2);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "a");
        CompletableFuture<String> future = future1.thenCombine(future2, (i, s) -> i + s);

        System.out.println(future.get());
    }

}
