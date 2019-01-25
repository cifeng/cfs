package com.platform.cfs.config.database;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘非
 * @ClassName PageInfo
 * @Description 分页使用的返回对象
 * @date 2019-01-02 14:21
 */
@Data
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int total;
    private T t;

    public PageInfo() {
    }

    public PageInfo(int total, T t) {
        super();
        this.total = total;
        this.t = t;
    }

}
