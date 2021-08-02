package org.wei.demo.async;


public class MyRunnable implements Runnable {

    private final String threadName;

    public MyRunnable(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + "-" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
