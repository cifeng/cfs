package com.platform.cfs.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImportExcel {

    /**
     * 导入时，对应数据库的字段 主要是用户区分每个字段，不能有annocation重名的
     */
    public String fieldName();

    /**
     * 用于调整顺序
     * @return
     */
    public int order();
}
