package org.wei.demo.monday;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    private String myName;

    public MyCallable(String myName) {
        this.myName = myName;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        return myName;
    }
}
