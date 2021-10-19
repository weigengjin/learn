package org.wei.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock接口的lock()和unLock()
 */
public class printABCByLock {

    private static final Lock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {

        Thread a = new Thread(() -> {
            try {
                lock.lock();
                // conditionA.await();
                for (int i = 0; i < 3; i++) {
                    System.out.println("A");
                    conditionB.signal();
                    conditionA.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread b = new Thread(() -> {
            try {
                lock.lock();
                // conditionB.await();
                for (int i = 0; i < 3; i++) {
                    System.out.println("B");
                    conditionC.signal();
                    conditionB.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread c = new Thread(() -> {
            try {
                lock.lock();
                // conditionB.await();
                for (int i = 0; i < 3; i++) {
                    System.out.println("C");
                    conditionA.signal();
                    conditionC.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        a.start();
        // Thread.sleep();
        b.start();
        c.start();
    }
}
