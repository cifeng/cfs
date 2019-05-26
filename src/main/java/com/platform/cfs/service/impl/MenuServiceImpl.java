package com.platform.cfs.service.impl;

import com.github.pagehelper.PageHelper;
import com.platform.cfs.constant.CacheConstant;
import com.platform.cfs.entity.MenuEntity;
import com.platform.cfs.mapper.MenuMapper;
import com.platform.cfs.service.IMenuService;
import com.platform.cfs.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description 菜单功能业务层实现类
 * @author 刘非
 * @date 2017-09-22 14:13
 */
@Service
public class MenuServiceImpl implements IMenuService{
    private Logger log= LoggerFactory.getLogger(MenuServiceImpl.class);
    private static final  Integer PID=0;
    private  String key= CacheConstant.BACKSTAGE_SYSTEM_AUTHORITY;


    @Autowired
    private MenuMapper menuMapper;

    @Override
    public  List<MenuEntity> menuList() {
        //获取redis中的key值，如果存在则直接从缓存中获取数据
        List<MenuEntity> one=new ArrayList<MenuEntity>();
        //如果缓存中没有，则从数据库取数据，然后记录在缓存数据库中
        List<MenuEntity> authorities = menuMapper.selectByMenu(1);
        //给第一层赋值
        for (MenuEntity datum : authorities) {
            if(Utils.nulltoZero(MenuServiceImpl.PID)==Utils.nulltoZero(datum.getPid())){
                one.add(datum);
            }
        }
        //给第二层赋值
        getListByPid(authorities,one);
        //给第三层赋值
        for (MenuEntity item : one) {
            getListByPid(authorities,item.getChild());
        }
        //将数据放到缓存中
        log.info("将菜单的数据放到缓存中 参数为："+one.toString());
        return one;
    }

    public  void getListByPid(List<MenuEntity> Authoritys, List<MenuEntity> mylist){
        List<MenuEntity> list=new ArrayList<MenuEntity>();
        for (MenuEntity item : mylist) {
            for (MenuEntity authority : Authoritys) {
                if(Utils.nulltoZero(authority.getPid())==Utils.nulltoZero(item.getId())){
                    item.getChild().add(authority);
                }
            }
        }
    }




    @Override
    public List<MenuEntity> queryByList(MenuEntity entity) {
        if(entity!=null&&entity.getPageIndex()!=null&&entity.getPageSize()!=null) {
            PageHelper.startPage(entity.getPageIndex(), entity.getPageSize());
        }

        return menuMapper.queryByList(entity);
    }

    @Override
    public Integer queryByCount(MenuEntity entity) {
        return menuMapper.queryByCount(entity);
    }


    @Override
    public Integer save(MenuEntity entity) {
        Integer num=0;
        if(Utils.isNotNull(entity)){
            entity.setCreateTime(new Date(System.currentTimeMillis()));
            num= menuMapper.insertSelective(entity);
        }
        return num;
    }

    @Override
    public int delete(String ids) {
        int num=0;
        if(ids!=null&&!"".equals(ids)&&!",".equals(ids)) {
            String[] array = ids.split(",");
            //这里将字符串数组转成int数组是为了提高查询效率
            Integer[] ints = new Integer[array.length];
            for (int i = 0; i < array.length; i++) {
                ints[i] = Integer.parseInt(array[i]);
            }
            num = menuMapper.deleteByBatch(ints);
        }
        return num;
    }

    @Override
    public int edit(MenuEntity entity) {
        Integer num=0;
        if(Utils.isNotNull(entity)){
            entity.setUpdateTime(new Date(System.currentTimeMillis()));
            num= menuMapper.updateByPrimaryKeySelective(entity);
        }
        return num;
    }

    @Override
    public MenuEntity queryById(Integer id) {
        MenuEntity entity  = menuMapper.selectByPrimaryKey(id);
        return entity;
    }


}
