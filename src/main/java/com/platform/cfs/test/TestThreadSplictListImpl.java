package com.platform.cfs.test;

import com.platform.cfs.utils.IThreadSplitList;

import java.util.List;
import java.util.Map;

public class TestThreadSplictListImpl implements IThreadSplitList<String> {

    @Override
    public Object execMethod(List<String> list, Object... objs) {
        Map<String,String> map = (Map<String,String>)objs[0];
        System.out.println(Thread.currentThread().getName() + "线程：" + list);
        // 意思一下 表示处理自己的业务逻辑
        for (String s : list) {
            map.put(s,s);
        }
        return null;
    }
}
