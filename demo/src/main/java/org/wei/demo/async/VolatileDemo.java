package org.wei.demo.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class VolatileDemo {

    private volatile Integer integer = 0;

    private Integer add() {
        for (int i = 0; i < 10000; i++) {
            integer++;
            System.out.println(Thread.currentThread().getName() + ": " + integer);
        }
        return integer;
    }

    public void demo() throws ExecutionException, InterruptedException {
        final CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return add();
        });
        final CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return add();
        });

        final Integer i1 = future1.get();
        final Integer i2 = future2.get();
        System.out.println(i1);
        System.out.println(i2);
        System.out.println("result: " + integer);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.demo();
    }


}
