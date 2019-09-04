package com.platform.cfs.utils;

import java.util.List;

public interface IThreadSplitList<T> {

    Object execMethod(List<T> list,Object... objs);

}
