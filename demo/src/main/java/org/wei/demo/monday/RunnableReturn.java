package org.wei.demo.monday;

import lombok.Data;

import java.util.concurrent.FutureTask;

@Data
public class RunnableReturn implements Runnable{

    private String result;

    @Override
    public void run() {
        // 业务逻辑
        String taskResult = null;
        try {
            taskResult = task();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = taskResult;
    }

    // Return "OK"
    private String task() throws InterruptedException {
        Thread.sleep(1000);
        return "Ojbk";
    }

    /**
     * @see FutureTask#get()
     *
     */
    public static void main(String[] args) {
        RunnableReturn rr = new RunnableReturn();
        Thread thread = new Thread(rr);
        thread.start();
        String result  = rr.getResult();
        System.out.println("result=" + result);
    }
}
