package org.wei.demo.async;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class CompletableFutureDemo {


    public static ThreadPoolTaskExecutor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        int i = Runtime.getRuntime().availableProcessors();//获取到服务器的cpu内核
        executor.setCorePoolSize(i+1);//核心池大小
        executor.setMaxPoolSize(100);//最大线程数
        executor.setQueueCapacity(1000);//队列程度
        executor.setKeepAliveSeconds(1000);//线程空闲时间
        executor.setThreadNamePrefix("task-async");//线程前缀名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//配置拒绝策略
        return executor;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new CompletableFutureDemo().test();
        final long time1 = System.currentTimeMillis();
        final CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "a";
        });
        final String s = future1.get();
        System.out.println(s);//a
        final long time2 = System.currentTimeMillis();
        System.out.println(time2-time1);//2050

        final CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "b";
        });
        final long time3 = System.currentTimeMillis();
        System.out.println(time3-time2);//0

        Thread.sleep(2000);

        final long time4 = System.currentTimeMillis();
        System.out.println(time4-time3);//2013

        final String ss = future1.get();
        final String s1 = future2.get();

        final long time5 = System.currentTimeMillis();
        System.out.println(time5-time4);//2998
        System.out.println(time5-time1);//7061
        System.out.println(ss + ":" + s1);//a:b
    }

    public void test() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "a";
        }, getExecutor());
        final String s = future1.get();
        System.out.println("test: " + s);
    }

}
