package com.platform.cfs.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ThreadFactoryUtil {
    private static ExecutorService pool = null;

    private ThreadFactoryUtil() {
    }

    public static ExecutorService getThreadFactoryUtilInstance(int threadNum){
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("splitlist-pool-%d").build();
        if(pool == null){
            pool = new ThreadPoolExecutor(threadNum,threadNum,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(),factory,new ThreadPoolExecutor.AbortPolicy());
        }
        return pool;
    }


}
