package com.platform.cfs.utils;

import java.math.BigDecimal;

/**
 * @ClassName BigDecimalUtils
 * @Description 操作BigDecimal的工具类
 * @author cifeng
 * @date 2019-05-26 18:18
 */
public class BigDecimalUtils {

    /**
     *
     * @Title 减法处理
     * @author cifeng
     * @return 减完的数
     */
    public static String subtract(String s1,String s2){
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.subtract(b2).toPlainString();
    }

    /**
     *
     * @Title 加法处理
     * @author cifeng
     * @return 加完的数
     */
    public static String add(String s1,String s2){
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.add(b2).toPlainString();
    }

}
