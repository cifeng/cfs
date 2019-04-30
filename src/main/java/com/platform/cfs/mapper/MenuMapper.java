package com.platform.cfs.mapper;

import com.platform.cfs.config.database.BaseMapper;
import com.platform.cfs.entity.MenuEntity;

import java.util.List;

public interface MenuMapper extends BaseMapper{
    int deleteByBatch(Integer[] ids);
    int insertSelective(MenuEntity record);
    List<MenuEntity> selectByMenu(Integer menuFunction);
    MenuEntity selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(MenuEntity record);
    Integer queryByCount(MenuEntity entity);
}