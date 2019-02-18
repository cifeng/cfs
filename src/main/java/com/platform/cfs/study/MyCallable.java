package com.platform.cfs.study;

import java.util.concurrent.Callable;

public class MyCallable<T>  implements Callable{
    private String name;
    public MyCallable(String name){
        this.name = name;
    }
    @Override
    public Object call() throws Exception {
        Integer sum = 0;
        for (int i = 0; i < 1000; i++) {
            System.out.println(name+":"+i);
            sum+=i;
        }
        return sum;
    }
}
