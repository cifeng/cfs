package com.platform.cfs.service;

import com.platform.cfs.entity.MenuEntity;

import java.util.List;

/**
 * @ClassName: IAuthorityService
 * @Description: 
 * @author: 刘非
 * @date: 2017-09-22 13:43
 * @version: V1.0
 */
public interface IMenuService {

    /**
     * @Title menuList
     * @Description 获取菜单数据
     * @author 刘非
     * @return java.util.List<com.wtoip.platform.porsche.entity.system.MenuEntity>
     */
    List<MenuEntity> menuList();

    /**
     * @Title queryByList
     * @Description 查询菜单数据
     * @author 刘非
     * @param entity 查询条件
     * @return java.util.List<com.wtoip.platform.porsche.entity.system.MenuEntity>
     */
    List<MenuEntity> queryByList(MenuEntity entity);

    /**
     * @Title queryByCount
     * @Description 获取总行数
     * @author 刘非
     * @param entity 查询条件
     * @return java.lang.Integer 总行数
     */
    Integer queryByCount(MenuEntity entity);

    /**
     * @Title save
     * @Description 保存菜单数据
     * @author 刘非
     * @param entity 菜单对象
     * @return java.lang.Integer
     */
    Integer save(MenuEntity entity);

    /**
     * @Title delete
     * @Description 批量删除
     * @author 刘非
     * @param ids 主键拼接字符串 格式:1,2,3...
     * @return int 受影响行数
     */
    int delete(String ids);

    /**
     * @Title edit
     * @Description 修改菜单数据
     * @author 刘非
     * @param entity 菜单对象
     * @return int 受影响行数
     */
    int edit(MenuEntity entity);

    /**
     * @Title queryById
     * @Description 获取菜单对象
     * @author 刘非
     * @param id 主键
     * @return com.wtoip.platform.porsche.entity.system.MenuEntity
     */
    MenuEntity queryById(Integer id);

    /**
     * @Title exportMethod
     * @Description 导出菜单数据
     * @author 刘非
     * @param entity 导出条件
     * @return org.apache.poi.hssf.usermodel.HSSFWorkbook
     */
//    HSSFWorkbook exportMethod(MenuEntity entity);
}
