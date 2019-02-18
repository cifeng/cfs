package com.platform.cfs.study;

import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new FutureTask(new MyCallable("A")));
        Thread t2 = new Thread(new FutureTask(new MyCallable("B")));
        t1.start();
        t2.start();

    }
}
