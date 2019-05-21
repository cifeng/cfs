package com.platform.cfs.config.database;

import java.util.List;

/**
 *
 * @param <T>
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    List<T> queryByList(T t);

     T queryById(String id);

    int save(T t);

    int update(T t);

    int delete(T t);

}
