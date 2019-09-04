package com.platform.cfs.test;

import com.platform.cfs.utils.listUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Test {


    public static void main(String[] args) throws Exception {
        // 假设这个map是处理自己业务逻辑的参数，可以写很多个 注意:如果是容器的话需要考虑并发场景
        Map<String,String> map = new ConcurrentHashMap<>();

        // 模拟数据List
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 3200; i++) {
            list.add(i + "");
        }
        // 实际业务可以进行注入不同的接口实现类 来处理不同的业务逻辑
        TestThreadSplictListImpl testThreadSplictList = new TestThreadSplictListImpl();
        // 调用分割集合方法，第一个参数是处理业务逻辑的实现类，第二个是要分割的集合，第三个多少数据进行分割，后面的是处理自己业务用的参数
        listUtil.threadSplitList(testThreadSplictList,list,200,map);
    }




}
