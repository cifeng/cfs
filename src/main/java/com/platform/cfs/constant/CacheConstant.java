package com.platform.cfs.constant;


/**
 * @ClassName CacheConstant
 * @Description 记录缓存的常量
 * @author cifeng
 * @date 2019-04-30 22:50
 */
public class CacheConstant {
    public static final String REDIS_BACKSTAGE = "cfs:member";
    //具体业务常量
    public static final String BACKSTAGE_SYSTEM_AUTHORITY=REDIS_BACKSTAGE+":system:menu";//系统菜单缓存key 存储格式 map


}
