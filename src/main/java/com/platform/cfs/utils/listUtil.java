package com.platform.cfs.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Slf4j
public class listUtil {

    public static void threadSplitList(IThreadSplitList threadSplitList, List<?> list, int size, Object... objs){
        if(Utils.isEmpty(list)){
            return;
        }
        // 如果size为空则默认给个数值 默认500条数据开启一个线程
        if(size<=0){
            size = 500;
        }
        // 记录开始时间
        long time = System.currentTimeMillis();
        // 总数据条数
        int dataSize = list.size();
        // 线程数
        int threadNum = dataSize / size + 1;
        // 定义标记
        boolean special = dataSize % size == 0;
        // 创建线程池
        ExecutorService exec = ThreadFactoryUtil.getThreadFactoryUtilInstance(threadNum);
        try {
            // 定义任务集合
            List<Callable<Object>> tasks = new ArrayList<>();
            Callable task = null;
            List<?> cutList = null;
            // 分割集合数据，放入对应的线程中
            for (int i = 0; i < threadNum; i++) {
                // 判断是否是最后一个线程
                if(i == threadNum-1){
                    if(special){
                        break;
                    }
                    cutList = list.subList(size * i,dataSize);
                }else{
                    cutList = list.subList(size * i,size * (i + 1));
                }
                final List<?> tempList = cutList;
                // 创建任务
                task = new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return threadSplitList.execMethod(tempList,objs);
                    }
                };
                tasks.add(task);
            }
            exec.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(log.isDebugEnabled()){
                log.debug("线程任务执行结束,执行任务消耗了:{}毫秒",(System.currentTimeMillis()-time));
            }
        }


    }


}
