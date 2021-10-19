package org.wei.demo.thread;

public class ThreadLocalDemo {

    /**
     * ThreadLocal 对象，虽然是个公共的静态变量，但是每个线程get到的值是不一样的
     */
    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            tl.set("11111");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1: " + tl.get());
        });

        Thread t2 = new Thread(() -> {
            tl.set("22222");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2： " + tl.get());
        });
        t1.start();
        Thread.sleep(500);// 预留剩下500ms时间让t2修改tl的值用于验证是否使用的同一个Key
        t2.start();
        System.out.println("main: " + tl.get());
    }
}
