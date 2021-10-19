package org.wei.demo.thread;

/**
 * 同理可以改成Lock接口的lock()和unLock()方法
 */
public class PrintABCBySyn {

    private static Integer status = 0;

    public static void main(String[] args) {
        Object monitor = new Object();
        Thread t1, t2, t3;
        t1 = new Thread(() -> {
            int i = 10;
            while (i > 0) {
                if (status != 0) {
                    continue;
                }
                synchronized (monitor) {
                    System.out.println("A" + i);
                    i--;
                    status = 1;
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
            // while (i > 0 && status == 1) {
            while (i > 0) {
                if (status != 1) {
                    // System.out.println("wati 1");
                    continue;
                }
                synchronized (monitor) {
                    System.out.println("B" + i);
                    i--;
                    status = 2;
                    monitor.notifyAll();
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t3 = new Thread(() -> {
            int i = 10;
            // while (i > 0 && status == 2) {
            while (i > 0) {
                if (status != 2) {
                    continue;
                }
                synchronized (monitor) {
                    System.out.println("C" + i);
                    i--;
                    status = 0;
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
        t3.start();
    }

}
