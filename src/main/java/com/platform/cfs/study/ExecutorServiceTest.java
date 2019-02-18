package com.platform.cfs.study;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTest {
    public static void main(String[] args)  {
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            // 支持Callable<T>,Runnable
            Future task = service.submit(new MyCallable("A"));
            // 可以拿到线程执行完的返回值,注意类型转换，返回值类型同call方法的返回值类型一致
            Object obj =task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            service.shutdown();
        }
    }
}
