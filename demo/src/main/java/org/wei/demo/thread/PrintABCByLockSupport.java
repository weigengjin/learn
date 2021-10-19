package org.wei.demo.thread;

import java.util.concurrent.locks.LockSupport;

public class PrintABCByLockSupport {

    Thread t1, t2, t3;

    public static void main(String[] args) {
        PrintABCByLockSupport o = new PrintABCByLockSupport();
        o.t1 = new Thread(() -> {
            LockSupport.park();
            int i = 10;
            while (i > 0) {
                System.out.println("A" + i);
                i--;
                LockSupport.unpark(o.t2);
                LockSupport.park();
            }
        });

        o.t2 = new Thread(() -> {
            LockSupport.park();
            int i = 10;

            while (i > 0) {
                System.out.println("B" + i);
                i--;
                LockSupport.unpark(o.t3);
                LockSupport.park();
            }

        });

        o.t3 = new Thread(() -> {
            LockSupport.park();
            int i = 10;

            while (i > 0) {
                System.out.println("C" + i);
                i--;
                LockSupport.unpark(o.t1);
                LockSupport.park();
            }

        });

        o.t1.start();
        o.t2.start();
        o.t3.start();
        LockSupport.unpark(o.t1);
    }

}
