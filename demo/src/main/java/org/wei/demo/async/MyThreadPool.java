package org.wei.demo.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    public static ThreadPoolExecutor getExecutor() {
        return new ThreadPoolExecutor(
                4,
                5,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("通过线程池创建线程");

        // 通过线程池创建线程
        getExecutor().execute(() -> System.out.println(Thread.currentThread().getName()));

    }
}
