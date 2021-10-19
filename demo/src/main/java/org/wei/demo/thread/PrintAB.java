package org.wei.demo.thread;

/**
 * 两个线程交叉打印A和B
 */
public class PrintAB {

    public static void main(String[] args) {
        Object monitor = new Object();
        Thread t1, t2;
        t1 = new Thread(() -> {
            int i = 10;
            while (i > 0) {
                synchronized (monitor) {
                    i--;
                    System.out.println("A");
                    monitor.notifyAll();
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t2 = new Thread(() -> {
            int i = 10;
            while (i > 0) {
                synchronized (monitor) {
                    i--;
                    System.out.println("B");
                    monitor.notifyAll();
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        t1.start();
        t2.start();
    }


}
