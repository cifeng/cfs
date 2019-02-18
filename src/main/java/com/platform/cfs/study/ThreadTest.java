package com.platform.cfs.study;

public class ThreadTest {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("A");
        MyThread t2 = new MyThread("B");
        t1.start();
        t2.start();
    }
}
