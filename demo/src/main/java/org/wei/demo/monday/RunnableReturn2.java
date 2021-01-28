package org.wei.demo.monday;


import lombok.NoArgsConstructor;

import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

@NoArgsConstructor
public class RunnableReturn2 implements Runnable {

    private volatile String result;
    private Thread blockedThread;

    RunnableReturn2(Thread thread) {
        blockedThread = thread;
    }


    @Override
    public void run() {
        // 执行业务逻辑
        result = task();
        System.out.println("业务执行完毕");
        LockSupport.unpark(blockedThread);
    }

    public String get() {
        if (result == null) {
            blockedThread = Thread.currentThread();
            System.out.println("getter waiting...");
            LockSupport.park();
            System.out.println("getter waiting end...");
        }
        return result;
    }

    // Return "OK"
    private String task() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Ojbk";
    }

    /**
     * @see FutureTask#get()
     */
    public static void main(String[] args) {
        RunnableReturn2 rr = new RunnableReturn2(Thread.currentThread());
        Thread thread = new Thread(rr);
        thread.start();
        String result = rr.get();
        System.out.println("result=" + result);
    }
}
