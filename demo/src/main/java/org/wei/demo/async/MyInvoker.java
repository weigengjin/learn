package org.wei.demo.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyInvoker {
    static Map<Integer, String> inheritableThreadLocal = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        inheritableThreadLocal.put(1, "1");
        // MyThread Test
        MyThread t1 = new MyThread("thread1");
        MyThread t2 = new MyThread("thread2");
        t1.setPriority(1);
        t1.start();
        t2.start();

        // MyRunnable Test
        MyRunnable r1 = new MyRunnable("run1");
        MyRunnable r2 = new MyRunnable("run2");
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r2);
        thread1.start();
        thread2.start();

        // MyCallable Test
        MyCallable c1 = new MyCallable("call1");
        MyCallable c2 = new MyCallable("call2");
        FutureTask<String> ft1 = new FutureTask<>(c1);
        FutureTask<String> ft2 = new FutureTask<>(c2);
        Thread th1 = new Thread(ft1);
        Thread th2 = new Thread(ft2);
        final long start = System.currentTimeMillis();
        th1.start();
        th2.start();
        System.out.println(ft1.get());
        System.out.println(ft2.get());
        final long end = System.currentTimeMillis();
        System.out.println("time spend: " + (end - start)/1000);

        new Thread(() -> {
            inheritableThreadLocal.get("");
        });
    }
}
