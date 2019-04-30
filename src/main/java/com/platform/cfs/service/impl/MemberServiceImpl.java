package com.platform.cfs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.config.database.PageVO;
import com.platform.cfs.constant.Const;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.mapper.SystemUserMapper;
import com.platform.cfs.service.IMemberService;
import com.platform.cfs.utils.Jackson2Helper;
import com.platform.cfs.utils.KeyUtil;
import com.platform.cfs.utils.MD5Util;
import com.platform.cfs.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MemberServiceImpl implements IMemberService {


    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public Response save(SystemUser user) {
        user.setId(KeyUtil.generatorUUID());
        // 设置初始密码
        final String initPwd = "123456";
        user.setPassword(MD5Util.MD5(initPwd));
        user.setState(Const.DATA_EFFECTIVE);
        user.setCreateTime(new Date());
        if(Utils.isNotEmpty(user.getBalance())&&Utils.nulltoZero(user.getBalance())>0){
            user.setLastTime(new Date());
        }
        log.info("创建会员====》写入数据库开始====》参数:{}", Jackson2Helper.toJsonString(user));
        int num = systemUserMapper.save(user);
        log.info("创建会员《====写入数据库结束====》受影响行数:{}", num);
        if(num>0){
            return ResponseUtil.buildResponse();
        }
        return ResponseUtil.buildErrorResponse();
    }


    @Override
    public Response edit(SystemUser user) {
        user.setUpdateTime(new Date());
        if(Utils.isNotEmpty(user.getBalance())&&Utils.nulltoZero(user.getBalance())>0){
            user.setLastTime(new Date());
        }
        log.info("修改会员====》写入数据库开始====》参数:{}", Jackson2Helper.toJsonString(user));
        int num = systemUserMapper.save(user);
        log.info("修改会员《====写入数据库结束====》受影响行数:{}", num);
        if(num>0){
            return ResponseUtil.buildResponse();
        }
        return ResponseUtil.buildErrorResponse();
    }

    @Override
    public Response delete(String ids) {
        String[] array = ids.split(",");
        log.info("删除会员====》操作数据库开始====》参数:{}", Jackson2Helper.toJsonString(array));
        Integer num = systemUserMapper.deleteByBatch(array);
        log.info("删除会员《====操作数据库结束《====受影响行数:{}", num);
        if(num>0){
            return ResponseUtil.buildResponse();
        }
        return ResponseUtil.buildErrorResponse();
    }

    @Override
    public SystemUser queryById(String id) {
        return  systemUserMapper.queryById(id);
    }

    @Override
    public PageVO queryByList(SystemUser user) {
        Page<PageInfo> pageInfo = PageHelper.startPage(user.getPageIndex(), user.getPageSize());
        log.info("查询会员列表开始====》参数为:{}",Jackson2Helper.toJsonString(user));
        List<SystemUser> list = systemUserMapper.queryByList(user);
        log.info("查询会员列表结束《====结果为:{}",Jackson2Helper.toJsonString(list));
        return new PageVO(pageInfo.getTotal(),list);
    }
}
