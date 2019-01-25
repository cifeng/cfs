package com.platform.cfs.config.database;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ClassName BaseMapper
 * @Description 基础数据库服务
 * @author 刘非
 * @date 2019-01-14 18:17
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
