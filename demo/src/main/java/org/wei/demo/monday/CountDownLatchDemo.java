package org.wei.demo.monday;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        System.out.println("主线程开始执行…… ……");
        //第一个子线程执行
        final long start = System.currentTimeMillis();
        final ThreadPoolExecutor executor = MyThreadPool.getExecutor();
        executor.execute(
                () -> {
                    try {
                        Thread.sleep(3000);
                        System.out.println("子线程：" + Thread.currentThread().getName() + "执行业务1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
        );

        executor.execute(
                () -> {
                    try {
                        Thread.sleep(3000);
                        System.out.println("子线程：" + Thread.currentThread().getName() + "执行业务2");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
        );
        System.out.println("等待两个线程执行完毕…… ……");
        try {
            latch.await();
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final long end = System.currentTimeMillis();
        System.out.println("两个子线程都执行完毕，耗时：" + (end - start));
    }

}
