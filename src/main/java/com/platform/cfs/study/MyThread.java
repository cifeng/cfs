package com.platform.cfs.study;

public class MyThread extends  Thread {
    private String name;
    public MyThread(String name){
        this.name = name;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(name+":"+i);
        }
    }
}