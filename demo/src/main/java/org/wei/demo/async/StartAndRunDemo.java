package org.wei.demo.async;

public class StartAndRunDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                task();
            }
        };
        System.out.println("CurrentThread name: " + Thread.currentThread().getName());;
        t.run();
        Thread.sleep(1000);
        t.start();
    }

    public static void task() {
        System.out.println("Running task thread name: " + Thread.currentThread().getName());
    }
}
