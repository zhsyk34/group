package com.cat.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompleteTest {

    private CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
        sleep();
        int i = 1 / 5;
        i = 100;
        return i;
    });

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.completedFuture(100);
//        System.out.println(new CompleteTest().completableFuture.join());
//        System.out.println(new CompleteTest().completableFuture.get());
    }

    private void sleep() {
        try {
            System.out.println("sleeping...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
